#!/bin/bash
#==================================
# configuration sector
#==================================

# 获取APP所在的目录的绝对路径
function get_abs_dir() {
    SOURCE="${BASH_SOURCE[0]}"
    # resolve $SOURCE until the file is no longer a symlink
    while [ -h "$SOURCE" ]; do
        TARGET="$(readlink "$SOURCE")"
        if [[ ${SOURCE} == /* ]]; then
            # echo "SOURCE '$SOURCE' is an absolute symlink to '$TARGET'"
            SOURCE="$TARGET"
        else
            DIR="$(dirname "$SOURCE")"
            # echo "SOURCE '$SOURCE' is a relative symlink to '$TARGET' (relative to '$DIR')"
            # if $SOURCE was a relative symlink, we need to resolve it
            # relative to the path where the symlink file was located
            SOURCE="$DIR/$TARGET"
        fi
    done
    # echo "SOURCE is '$SOURCE'"

    # RDIR="$( dirname "$SOURCE" )"
    DIR="$( cd -P "$( dirname "$SOURCE" )" && cd .. && pwd )"
    # if [ "$DIR" != "$RDIR" ]; then
    #     echo "DIR '$RDIR' resolves to '$DIR'"
    # fi
    # echo "DIR is '$DIR'"
    echo $DIR
}

APP_HOME=`get_abs_dir`
VERSION='[0-9]*.[0-9]*.[0-9]*-SNAPSHOT'
JAR_PATH=$(find ${APP_HOME} -maxdepth 1 -name "[a-z]*-${VERSION}.jar")
JAR=${JAR_PATH##*/}
MAIN_CLASS=

# *NOTE* 多网卡下不正确
IP="$( LC_ALL=C ifconfig  | grep 'inet addr:'| grep -v '127.0.0.1' |cut -d: -f2 | awk '{ print $1}' )"
COUNT=1
# 不同机器应配置不同的标识，建议用ip:counter形式表示。
APP_ID="${IP}:${COUNT}"
APP_CONF_DIR="${APP_HOME}/conf"
APP_ARGS=


# Add default JVM options here. You can also use JAVA_OPTS to pass JVM options to this script.
JAVA_OPTS="${JAVA_OPTS} -server -Xms512M -Xmx512M -Xss256K \
      -XX:PermSize=96m -XX:MaxPermSize=128m \
      -XX:+DisableExplicitGC -XX:SurvivorRatio=1 \
      -XX:GCTimeRatio=19 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC \
      -XX:+CMSParallelRemarkEnabled -XX:+CMSClassUnloadingEnabled \
      -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods \
      -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 \
      -XX:SoftRefLRUPolicyMSPerMB=0  -XX:CMSFullGCsBeforeCompaction=1 \
      -XX:+AggressiveOpts -XX:+PrintGCDetails -XX:+PrintGCTimeStamps \
      -XX:+HeapDumpOnOutOfMemoryError \
      -Dapp.home=${APP_HOME} -Dapp.id=${APP_ID} -Djava.awt.headless=true"

#OUT_FILE=${APP_HOME}/logs/${APP_NAME}-nuhuo.log
OUT_FILE=/dev/null
#RUNNING_PID="${APP_HOME}"/RUNNING_PID

# set classpath
classpath_munge () {
    if ! echo $CLASSPATH | /usr/bin/egrep -q "(^|:)$1($|:)" ; then
        if [ "$2" = "after" ] ; then
            CLASSPATH=$CLASSPATH:$1
        else
            CLASSPATH=$1:$CLASSPATH
        fi
    fi
}

#CLASSPATH=${APP_HOME}/conf:${APP_HOME}/${JAR}
#CLASSPATH=${CLASSPATH}:$(JARS=("$APP_HOME"/lib/*.jar); IFS=:; echo "${JARS[*]}")

classpath_munge ${APP_CONF_DIR}
classpath_munge ${APP_HOME}/\*

#==================================
# execute sector
#==================================

#mkdir -p ${APP_HOME}/logs


# colors
red='\e[0;31m'
green='\e[0;32m'
yellow='\e[0;33m'
reset='\e[0m'

echoRed() { echo -e "${red}$1${reset}"; }
echoGreen() { echo -e "${green}$1${reset}"; }
echoYellow() { echo -e "${yellow}$1${reset}"; }

warn ( )
{
    echoYellow "$*"
}

die ( )
{
    echo
    echoRed "$*"
    echo
    exit 1
}

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME
Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Escape application args
save ( ) {
    for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/' \\\\/" ; done
    echo " "
}

ACTION=$1
shift

# Check whether the application is running.
# The check is pretty simple: open a running pid file and check that the process
# is alive.
#检查程序是否在运行
is_exist()
{
    pid=`ps -ef|grep $JAR|grep -v grep|awk '{print $2}'`
     #如果不存在返回1，存在返回0     
    if [ -z "${pid}" ]; then
        return 1
    else
        return 0
    fi
}

#启动方法
start()
{
    is_exist
    if [ $? -eq "0" ]; then
	   echoYellow "---------------The ${JAR} is already running------------"
    else
            nohup "$JAVACMD" $JAVA_OPTS  -jar ${APP_HOME}/$JAR $APP_ARGS $@> ${OUT_FILE} 2>&1 & 
	   echoGreen "--------------The ${JAR} start success----------------------"
    fi
}

#重启方法
restart()
{
    echo "Restarting ${JAR}"
    stop
    start
}

#停止方法
stop()
{
    is_exist
    if [ $? -eq "0" ]; then
	   kill -9 $pid
	   echoGreen "${JAR} stop success"
    else
	   echo "${JAR} is not running"
    fi 
}

#查询运行状态方法
status() {
    is_exist
    if [ $? -eq "0" ]; then
	 echoGreen "${JAR} is running. Pid is ${pid}"
    else	
	 echoRed "${JAR} is NOT running."
    fi	
}

case "$ACTION" in
    start) 
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        start
        ;;
    status)
        status
        exit 0
        ;;
    *)
        printf 'Usage: %s {status|start|stop|restart}\n'
        exit 1
        ;;
esac
