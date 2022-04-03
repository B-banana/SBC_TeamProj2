<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--@ taglib prefix="form" uri="http://www.springframework.org/tags/form"--%>
<!-- 절대 경로 지정해주기.  -->
<c:set var="root" value="${pageContext.request.contextPath }/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="favicon.ico">
<title>SBC MAIN</title>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<!-- Bootstrap core CSS -->
<link href="${root }css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<!-- Custom styles for this template -->
<link href="${root }css/style.css" rel="stylesheet">

<!-- header -->
<c:import url='/WEB-INF/views/include/header_menu2.jsp' />

<style>

@font-face {
    font-family: 'Cafe24Dongdong';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.1/Cafe24Dongdong.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
*{
	font-family: 'Cafe24Dongdong' !important;
}
   
   

.img-r {
background-size: cover;
width: 100%;
height: 400px;
} 

.img-responsive {
   width: 100%;
   height: 300px;
}

.img-responsive-1 {
   position: relative;
   width: 350px;
   height: 350px;
}

.justify-content-center {
	position:relative;
	text-align: center;
   -ms-flex-pack: center!important;
   justify-content: center!important
}

footer {
	background: #FAEBD7;
}
</style>
<script>

$(document).ready(function() {
	
	$.ajax({
        "url" : "searchDog_pro",
        "type" : "get",
        "dataType" : "json",
        "async" : "false",
        "success" : function(resultMap) {
        	
              $('#dog_info_list1').empty();
              
              $('#modalDiv').empty();
              var modalId = '';
              var labelledby = '';
              
              $.each(resultMap.data,
              function(key,dogInfo) {
                            var html = '';
                            var html1 = '';
                            modalId = "Modal-" + (key + 1);
                            labelledby = "Modal-label-" + (key + 1);
							
                            document.getElementById("dog_info_list"+(key+1)).innerHTML = 
                            "<img src='"+dogInfo.popfile+"' class='img-responsive'/><figcaption><h2>"
                            + dogInfo.kindCd
                            + "</h2><p>"
                            + dogInfo.careNm
                            + "</p>"
                            + "<a href='#"+modalId+"' data-toggle='modal' data-target='#"+modalId+"'>"
                            + dogInfo.sexCd
                            + "</a></figcaption>";

                            html1 += "<div class='modal fade' id='"+ modalId +"'tabindex='-1' role='dialog' aria-labelledby='"+labelledby+"'><div class='modal-dialog' role='document'>"
                                  + "<div class='modal-content justify-content-center'><div class='modal-header'><button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
                                  + "<h4 class='modal-title' id='"+labelledby+"'>"
                                  + "<img src='${root}img/logo_sbc.jpg'/>"
                                  + "</h4></div><div class='modal-body'>"
                                  + "<img src='"+dogInfo.popfile+"' class='img-responsive-1'/>"
                                  + "<div class='modal-works'style='font-size:15px;'><span>"+ dogInfo.processState+ "</span><span>"+ dogInfo.kindCd+ "</span>"
                                  +"<span>"+dogInfo.age+"살</span><span>"+dogInfo.weight+"</span></div><div class='modal-body text-left' style='font-weight:900;'><p>공고번호: "+ dogInfo.noticeNo+"</p><p>공고기간: "+dogInfo.noticeSdt+" ~ "+dogInfo.noticeEdt +"</p><p>발견장소: "+dogInfo.happenPlace+"</p><p>특이사항 :"+dogInfo.specialMark+"</p><p>보호센터 : "+dogInfo.careNm+"("+dogInfo.careTel+")</p><p>담당부서 : "+dogInfo.orgNm+"("+dogInfo.officetel+")</p></div> <div class='modal-footer'><button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>"
                                  +"<button type='button' class='btn btn-default likebtn' onclick='hello()''>유기동물 조회</button>"
                                  +"</div></div></div></div><i id='idxi'>"+key+"</i></div>";
                           $('#modalDiv').append(html1);

                         }
             );//each	
            	  			 
       	},	//success

        "error" : function(x, o, e) {
           alert(x.status + ":" + o + ":" + e);
        }	//error
     }); //ajax 끝
	
});	//document.ready

function hello() {
	location.href = "${root}/board/searchDog";
}

function formSubmit(type) {
	$("#frm_"+type).submit();
}

</script>


</head>

<body id="page-top">


	<section id="team">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<div class="section-title">
						<h2 style="margin-top:4%">Welcome to SBC</h2>
						<p style="margin-bottom: 8%">유기동물 조회 사이트입니다. 따뜻한 가정을 찾아주세요.</p>
						<h3>최신글</h3>
					</div>
				</div>
			</div>
			<div class="row">
				<!-- team member item -->
				<c:forEach items='${list }' var='dog'>
				<div class="col-md-4">
					<div class="team-item">
						<div class="team-image "style="height: 300px; width: 100%">
							<a href="${root }board/read?b_no=${dog.board_num}">
								<img style=" object-fit:scale-down; height: 300px;" src="${root }upload/${dog.dog_img}" class="img-responsive" alt="author">
							</a>
						</div>
						<div class="team-text">
							<h3 style="border-radius:5px; background:#EBDD1E; color: gray; font-weight:900; margin-left:30%; width:40%; padding: 3px 0px 3px 0px;">${dog.board_category}</h3>
							<div class="team-position"style="font-weight: 600; font-size: 18px;">${dog.breed}
								<c:set var="sex" value="${dog.sex}" scope="session"/>
								<c:choose>
									<c:when test="${sex=='수컷'||sex==' 수컷'||sex=='수컷 '||sex==' 수컷 '}">
										<img src="${root}img/board/male.png">
									</c:when>

									<c:when test="${sex=='암컷'||sex==' 암컷'||sex=='암컷 '||sex==' 암컷 '}">
										<img src="${root}img/board/female.png">
									</c:when>
									
									<c:otherwise>
										<img src="${root}img/board/gender.png">
									</c:otherwise>
								</c:choose>
							</div>
							<p style="margin-top: -20px;">${dog.sex}&nbsp;/&nbsp;${dog.color }&nbsp;/&nbsp;age: ${dog.age }&nbsp;/&nbsp;kg: ${dog.weight}</p>
							<p style="margin-top: -40px;"><img src="${root }img/board/clock.png" style="max-width: 7%; max-height: 7%;"/>${dog.happen_date}&nbsp;</p>
							<p style="margin-top: -40px;"><img src="${root }img/board/place.png" style="max-width: 7%; max-height: 7%;"/>${dog.address1}&nbsp;</p>
							
                	 		
						</div>
					</div>
				</div>
				</c:forEach>
				
				
				<!-- end team member item -->
			</div>
		</div>
	</section>

	<!-- 여기서부터 진짜 body -->
	<section id="portfolio">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<div class="section-title">
						<h3>유기동물 조회</h3>
					</div>
				</div>
			</div>
			
			<div class="row row-0-gutter">
				<c:forEach begin="1" end="6" varStatus="vs">
				<!-- start portfolio item -->
				<div class="col-md-4 col-0-gutter">
					<div class="ot-portfolio-item">
						<figure class="effect-bubba">
							<div class="row" id="dog_info_list${vs.index }"></div>
						</figure> 
					</div>
				</div>
				<!-- end portfolio item -->
				</c:forEach>
			
			</div>
		</div>
		<!-- container -->
	</section>
	
	
	
	
	
	<section>
		<div class="container">
			<!-- 세번째 row : 샵 -->
			<div class="row">
				<div class="col-lg-12 text-center">
					<div class="section-title">
						<h3>쇼핑</h3>
					</div>
				</div>
			</div>
			
			<div class="row row-0-gutter">
				<c:forEach begin="1" end="3" varStatus="vs" var="list" items="${productList }">
					<form id="frm_${vs.index }" action="${root }product/productPage" method="post">
						<input type="hidden" name="product_name" value="${list.product_name }" />
						
						<div class="col-md-4 col-0-gutter">
							<div class="ot-portfolio-item">
								<figure class="effect-bubba">
									<div class="row">
										<!-- Product image-->
			                            <img class='img-responsive card-img-top' src="${root }images/product/${list.product_id}.jpg"/>
			                            <figcaption onclick="formSubmit(${vs.index})">
			                            	<h2>
			                            	${list.product_name } <br />
			                            	<fmt:formatNumber value="${list.price }" pattern="#,###"/>원<br>
			                            	</h2>
			                            	<p>상품 보러가기! Click!</p>
			                            </figcaption>
									</div>
								</figure> 
							</div>
						</div>
					</form>
				</c:forEach>
			</div>
		</div>
	</section>
	
	
	<p id="back-top">
		<a href="#top"><i class="fa fa-angle-up"></i></a>
	</p>

	<!-- footer  -->
	<c:import url='/WEB-INF/views/include/footer_menu2.jsp' />
	
	<div id="modalDiv"></div>

	<!-- Bootstrap core JavaScript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="${root }js/bootstrap.min.js"></script>
	<script src="${root }js/SmoothScroll.js"></script>
	<script src="${root }js/theme-scripts.js"></script>

</body>
</html>