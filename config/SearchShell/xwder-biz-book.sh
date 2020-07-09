#xwder-biz-book.sh book 服务重启
port=9001
#根据端口号查询对应的pid
pid=$(netstat -nlp | grep :$port | awk '{print $7}' | awk -F"/" '{ print $1 }');

#杀掉对应的进程，如果pid不存在，则不执行
if [  -n  "$pid"  ];  then
    kill  -9  $pid;
fi

nohup java -jar xwder-biz-book-1.0-SNAPSHOT.jar &
