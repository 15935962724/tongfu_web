$().ready(function() {

  var $listForm = $("#list");
  var $pageNum = $("#pageNum");
  var $pageSize = $("#pageSize");
  var $selectPageSize = $("#selectPageSize");

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



});