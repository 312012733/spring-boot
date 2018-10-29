var curPage = 0;//当前的页数（重0开始）
var condition;//分页条件json对象

function forwardStuAdd(){
	window.location = "/test/page/student.html?action=add";
}

function forwardUpdateStudent(stuId){
	window.location = "/test/page/student.html?action=update&stuId="+stuId;
}

function forwardFindStudent(stuId){
	window.location = "/test/page/student.html?action=find&stuId="+stuId;
}

function forwardStuList(){
	window.location = "/test/page/studentList.html";
}

function forwardUpload(){
	window.location = "/test/page/uploadAndDownload.html";
}


function getQueryParamValue(key){
	var queryParmStr = window.location.search.substring(1);
	
	var reg = new RegExp("&?"+key+"=([^&]+)");
	
	var results = queryParmStr.match(reg);
	
	if(results){
		return decodeURIComponent(results[1]);
	}
	
	return null;
	
}