$().ready(function() {

  var $listForm = $("#list");
  var $pageNum = $("#pageNum");
  var $pageSize = $("#pageSize");
  var $selectPageSize = $("#selectPageSize");
  var $jump = $("#jump");//跳转按钮
  var $jumpPageNumber = $("#jumpPageNumber");//跳转页码

  var $couuntPageSize = $("#couuntPageSize").val(); //总页数

  // 上一页，下一页
  $.pageSkip = function(pageNumber) {
      $pageNum.val(pageNumber);
      $listForm.submit();
      return false;
  }

  // 每页显示
  $selectPageSize.bind("change", function () {
    var  $this = $("#selectPageSize option:selected");
      $pageSize.val($this.val());
      $pageNum.val("1");
      $listForm.submit();
      return false;
  });

  $jump.bind("click",function () {
    var jumpPageNumber = $jumpPageNumber.val();
    if (jumpPageNumber<=0){
      alert("输入页码不合理");
      return false;
    }
    if(Number(jumpPageNumber) > Number($couuntPageSize)){
      alert("输入页码不能大于"+$couuntPageSize);
      return false;
    }

    $pageNum.val(jumpPageNumber);
    $listForm.submit();
    return false;
  });


});