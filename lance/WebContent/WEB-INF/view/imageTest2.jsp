<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Test</title>

<style type="text/css">
			* { margin:0; padding:0; }

		.wrap { width:950px; margin:0 auto; padding-bottom:400px; }
		#top_menu { position: fixed; width:950px; height:114px; margin:0 auto; text-align:center; top:0px; background: #ffffff; }
		#top_menu ul {float:right; margin-right:33px;}
		#top_menu ul li { padding:59px 0px 0px 63px; float:left; list-style: none; color: #CCCCCC; }
		#top_menu ul li a { color:#fff; text-decoration:none; }
		#top_menu ul li.on a { color:#000; font-weight:bold; } 
		#logo {float:left; width:125px; padding-top:40px; color:#FFFFFF;}
		
		#contents {}
		#contents div {padding-top:114px;}
		#paper {width:100%; height:650px;background:#00BAFF;}
		#works {width:100%; height:650px; background:#00BAFF;}
		#email {width:100%; height:700px;background:#FFE200; }
		#gallery { position:absolute; left:50%; top:50%; margin:-297px 0 0 -412px; width:1300px; height:595px;}
		#image { width:760px; height:470px; margin:0 auto; }
		#image p { display:none;}
		#image p img { width:760px; height:470px; }
		#thumbnail { position:relative; width:1080px; height:95px; margin-top:30px;}
		#thumbnail div { float: left; }
		#thumbnail .left { position:absolute; left:0; top:0; width:503px; height:95px; padding-top:30px; text-align:center;  cursor:pointer;}
		#thumbnail .right { position:absolute; right:0; top:0; width:-250px;  height:95px; padding-top:30px; text-align:center; cursor:pointer;}
		#thumbnail .container { overflow: hidden; position:relative; left:270px; width:760px; height:95px;}
		#thumbnail .container ul { position: absolute; width:2560px; margin-left:-8px;}
		#thumbnail .container .thumb { float: left; width:120px; height:95px; margin-left:8px; list-style: none; }
		#thumbnail .container .thumb.ml0{ margin-left:0 }

</style>

 
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.easing.1.3.min.js"></script>
<script type="text/javascript" src="../js/scroll_menu.js"></script>
<script type="text/javascript" src="../js/gallery.js"></script>

</head>
<body>

		<div class="main">
		<!-- 여기서부터 작업 -->
		<div class="wrap">
			<div id="top_menu">
				<div id="logo">
					<a href="#home"><img src="../images/main_2_02.jpg" alt="Artatelier" /></a>
				</div>
				<ul>
					<li class="on"><a href="#works"><img src="../images/main_2_04.jpg" alt="1" /></a></li>
					<li><a href="#company"><img src="../images/main_2_05.jpg" alt="2" /></a></li>
					<li><a href="#email"><img src="../images/main_2_06.jpg" alt="3" /></a></li>
					<li><a href="#test1"><img src="../images/main_2_07.jpg" alt="4" /></a></li>
					<li><a href="#test2">test2</a></li>
				</ul>		
			</div>
			
			<div id="contents">
				<div id="paper"><img src="../images/main_2_13.jpg" alt="paper" /></div>
				<div id="works"><img src="../images/work.jpg" alt="works" /></div>
				<div id="email"><img src="../images/email.jpg" alt="email" /></div>
				<div id="test1"><img src="../images/company.jpg" alt="test1" /></div>
				<div id="test2"><img src="../images/0.jpg" alt="test2" /></div>
			</div>
		</div>
			<!--  
			<div id="gallery">
				<div id="image">
					<p style="display:block;"><img src="../images/0.jpg" alt="" /></p>
					<p><img src="../images/1.jpg" alt="" /></p>
					<p><img src="../images/2.jpg" alt="" /></p>
					<p><img src="../images/3.jpg" alt="" /></p>
					<p><img src="../images/4.jpg" alt="" /></p>
					<p><img src="../images/5.jpg" alt="" /></p>
					<p><img src="../images/6.jpg" alt="" /></p>
					<p><img src="../images/7.jpg" alt="" /></p>
					<p><img src="../images/8.jpg" alt="" /></p>
					<p><img src="../images/9.jpg" alt="" /></p>
					<p><img src="../images/10.jpg" alt="" /></p>
					<p><img src="../images/11.jpg" alt="" /></p>
					<p><img src="../images/12.jpg" alt="" /></p>
					<p><img src="../images/13.jpg" alt="" /></p>
					<p><img src="../images/14.jpg" alt="" /></p>
					<p><img src="../images/15.jpg" alt="" /></p>
					<p><img src="../images/16.jpg" alt="" /></p>
					<p><img src="../images/17.jpg" alt="" /></p>
					<p><img src="../images/18.jpg" alt="" /></p>
					<p><img src="../images/19.jpg" alt="" /></p>
				</div>
				<div id="thumbnail">
					<div class="left">
						<span><img src="../images/btn_left.gif" alt="이전" /></span>
					</div>
					
					<div class="container">
						<ul>
							<li class="thumb"><img src="../images/thumb0.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb1.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb2.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb3.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb4.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb5.jpg" alt="" /></li>
							<li class="thumb ml0"><img src="../images/thumb6.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb7.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb8.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb9.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb10.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb11.jpg" alt="" /></li>
							<li class="thumb ml0"><img src="../images/thumb12.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb13.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb14.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb15.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb16.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb17.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb18.jpg" alt="" /></li>
							<li class="thumb"><img src="../images/thumb19.jpg" alt="" /></li>
						</ul>
					</div>			
					<div class="right">
						<span><img src="../images/btn_right.gif" alt="다음" /></span>
					</div>
				</div>
			</div>
			-->
			
		</div>
</body>
</html>