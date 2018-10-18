<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Digital clack</title>
</head>
<body id="boby" BGCOLOR=blue><font size=4 color=red>
<form id="all">
    <h1 id="time">leading</h1>
    <h2 id="datetime"></h2>
</form>    
<h1 id="str_time"></h1>

<script>
window.online = function(){
    refresh_time();
};

function refresh_time() {
    var d = new Date();
    var str_time = Date();
    var hours = d.getHours();
    var mins = d.getMinutes();
    var secs = d.getSeconds();
    var day = d.getDay();
    var date = d.getDate();
    var month = d.getMonth();
    var year = d.getFullYear();

    //console.log(month);
    document.getElementById("time").innerHTML = hours+":"+mins+":"+secs;
    //document.getElementById("datetime").innerHTML = day +"," + date + "," + month + "," + year;
    document.getElementById("datetime").innerHTML = date + "," + month + "," + year;
    //document.getElementById("str_time").innerHTML = str_time;
}
setInterval(refresh_time,1000);

</script>

<%! long i = 0; %>
<% i++; %>
<p>你是第<%=i %>个访问本站的客户</p>
</font>

</body>
</html>