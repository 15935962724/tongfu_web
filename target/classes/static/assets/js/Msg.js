
  function showTip(msg, sec){
    if(!sec) {
      sec = 1000;
    }
    Modal.tip({
      title:'提示',
      msg: msg
    }, sec);
  }


  /**
   * 显示提示消息（自动关闭）
   * @param msg
   * @param sec 显示时间（毫秒）
   * @param callback 回调函数
   */
  function showTip(msg, sec, callback){
    if(!sec) {
      sec = 1000;
    }
    Modal.tip({
      title:'提示',
      msg: msg
    }, sec);
    setTimeout(callback, sec);
  }

  /**
   * 显示消息
   * @param msg
   */
  function showMsg(msg, callback){
    Modal.alert({
      title:'提示',
      msg: msg,
      btnok: '确定'
    }).on(function (e) {
      if(callback){
        callback();
      }
    });
  }

  /**
   * 模态对话框
   * @param msg
   * @returns
   */
  function showConfirm(msg,callback){
    //var res = false;
    Modal.confirm(
        {
          title:'提示',
          msg: msg,
        }).on( function (e) {
      callback();
      //res=true;
    });
    //return res;
  }

