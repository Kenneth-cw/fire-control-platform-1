function setConnected() {

}

/*function connect() {
    if (!url) {
        //alert('Select whether to use W3C WebSocket or SockJS');
        return;
    }
    // 打开一个 web socket
    ws = (url.indexOf('sockjs') != -1) ? new SockJS(url, undefined, {protocols_whitelist: transports}) : new WebSocket(url);
    ws.onopen = function () {
        // Web Socket 已连接上（可以使用send()方法发送数据）
        setConnected(true);
    }
    ws.onmessage = function (event) {

    }
    ws.onclose = function (event) {
        setConnected(false);
    }
}*/

// webSocket连接 函数
function websocketConnect() {
    if (!url) {
        console.log('Select whether to use W3C WebSocket or SockJS');
        return;
    }
    // 打开一个 web socket 连接
    ws = (url.indexOf('sockjs') != -1) ? new SockJS(url, undefined, {protocols_whitelist: transports}) : new WebSocket(url);

    // Web Socket 已连接上（可以使用send()方法发送数据）
    ws.onopen = function () {
        setConnected(true);
    };

    // 接收到服务器发来的消息了
    ws.onmessage = function (event) {
        if (event.data == "pong") {
            console.log("pong");
            clearInterval(interval);
            websocket_openInterval();
        } else if (event.data == "重复登录") {
            alert("下线提醒:该账号已在其他地方登录,即将下线");
            window.location.href = "/iotadmin";
        } else {
            console.log("处理非心跳包数据");
            var data = eval('(' + event.data + ')');
            console.log(data);
            getItemType(data);
        }
    };

    // 关闭 socket 连接
    ws.onclose = function (event) {
        setConnected(false);
        //log(event);
    };
}

/*function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
    setConnected(false);
}*/

function websocket_openInterval() {
    interval = setInterval(function () {
        console.log("重连websocket");
        updateUrl('/iotadmin/websocket');
        websocketConnect();
        clearInterval(heartInterval);
        websocket_timer();
        clearInterval(interval);
        websocket_openInterval();
    }, 55000)
}

function updateUrl(urlPath) {
    if (urlPath.indexOf('sockjs') != -1) {
        url = urlPath;
        document.getElementById('sockJsTransportSelect').style.visibility = 'visible';
    } else {
        if (window.location.protocol == 'http:') {
            url = 'ws://' + window.location.host + urlPath;
        } else {
            url = 'wss://' + window.location.host + urlPath;
        }
        //document.getElementById('sockJsTransportSelect').style.visibility = 'hidden';
    }
}

function websocket_timer() {
    heartInterval = setInterval(function () {
        ws.send("ping");
    }, 50000);//五十秒发一次心跳
}