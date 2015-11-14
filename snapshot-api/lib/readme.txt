
使用帮助（参考DEMO工程）

1. 将解压以后LIB里的jar包拷贝DEMO工程里lib文件下并构建到路径里
2. 打开DEMO工程里的测试类 将调用CCPRestSDK的init和相关功能接口，参数换成实际的参数即可


接口说明

<pre>
 /**
	 * 初始化服务地址和端口  地址须为sandboxapp.cloopen.com 无需带协议头，目前默认是https协议
	 * @param serverIP     		必选参数		服务器地址 
	 * @param serverPort		必选参数		服务器端口
	 */
	public void init(String serverIP, String serverPort)
  
  
  /**
	 * 初始化主帐号信息
	 * @param accountSid		必选参数		主帐号
	 * @param accountToken		必选参数		主账号令牌
	 */
	public void setAccount(String accountSid, String accountToken)
	
	/**
	 * 初始化子帐号信息
	 * @param subAccountSid		必选参数		 子帐号
	 * @param subAccountToken	必选参数		 子账号令牌
	 */
	public void setSubAccount(String subAccountSid, String subAccountToken)
	
	/**
	 * 初始化应用Id	
	 * @param appId			必选参数	 	应用Id
	 */
	public void setAppId(String appId)
	
	/**
	 * 初始化VoIP帐号信息
	 * @param voIPAccount    	必选参数 		VoIP帐号
	 * @param voIPPassWord	  	必选参数		VoIP密码
	 */	
	public void setVoIPAccount(String voIPAccount, String voIPPassWord)
  
 /**
   * 获取主帐号信息查询
   * @return
   */
  public HashMap queryAccountInfo()
  
  
 /**
   * 创建子帐号
   * @param friendlyName		必选参数  		子账户名称。可由英文字母和阿拉伯数字组成子账户唯一名称，推荐使用电子邮箱地址
   * @return
   */
   public HashMap createSubAccount(String friendlyName)
   
   
 /**
   * 获取子帐号
   * @param startNo			必选参数  		开始的序号，默认从0开始
   * @param offset 			必选参数  		一次查询的最大条数，最小是1条，最大是100条
   * @return
   */
  public HashMap getSubAccounts(String startNo, String offset)
  
   
 /**
   * 获取子帐号信息 	
   * @param friendlyName 		必选参数		子帐号名称	
   * @return
   */
  public HashMap querySubAccount(String friendlyName)
  
  
  
 /**
   免费开发测试注意事项：
   1.免费开发测试需要使用"控制台—应用—测试demo"下相关信息，如主账号，应用ID。
   2.免费开发测试使用的模板ID为1，形式为：【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入，{1}和{2}为短信模板的参数。
   3.免费开发测试需要使用沙盒环境。
   4.免费开发测试需要在"控制台—应用—号码管理—测试号码"绑定测试号码。
   5.开发测试过程请参考 开发指南 及 Demo示例 。
   
   测试没有问题后，你就可以切换到生产环境了【如果您还没有创建自己的应用，请按"创建应用—充值—创建短信模板"的步骤来操作】
   切换到正式环境注意事项：
   1.将Base URL 由沙盒环境（https://sandboxapp.cloopen.com:8883）替换到生产环境（https://app.cloopen.com:8883）
   2.将AppId由"测试Demo"的AppId改为自己创建的应用的AppId。
   3.将模板ID由测试模板ID 1 改为自己创建的模板ID。
   
   * 发送短信模板请求
   * @param to				必选参数   		短信接收端手机号码集合，用英文逗号分开，每批发送的手机号数量不得超过100个
   * @param templateId			必选参数		模板Id 
   * @param datas			可选参数		内容数据，用于替换模板中{序号}
   * @return
   */
  public HashMap sendTemplateSMS(String to, String templateId, String[] datas)
  
  
	/**
	 * 发送双向回拨请求
	 * 
	 * @param from
	 *            必选参数 主叫电话号码
	 * @param to
	 *            必选参数 被叫电话号码
	 * @param customerSerNum
	 *            可选参数 被叫侧显示的客服号码，根据平台侧显号规则控制
	 * @param fromSerNum
	 *            可选参数 主叫侧显示的号码，根据平台侧显号规则控制
	 * @param promptTone
	 *            可选参数 第三方自定义回拨提示音
	 * @param userData
	 *            可选参数 第三方私有数据
	 * @param maxCallTime
	 *            可选参数 最大通话时长
	 * @param hangupCdrUrl
	 *            可选参数 实时话单通知地址
	 * @param alwaysPlay
	 *            可选参数 是否一直播放提示音
	 * @param terminalDtmf
	 *            可选参数 用于终止播放提示音的按键
	 * @param needBothCdr
	 *            可选参数 是否给主被叫发送话单
	 * @param needRecord
	 *            可选参数 是否录音
	 * @param countDownTime
	 *            可选参数 设置倒计时时间
	 * @param countDownPrompt
	 *            可选参数 到达倒计时时间播放的提示音
	 * @return
	 */
  public HashMap callback(String from, String to,String customerSerNum, String fromSerNum, String promptTone,String alwaysPlay, String terminalDtmf, String userData,String maxCallTime, String hangupCdrUrl, String needBothCdr,String needRecord, String countDownTime, String countDownPrompt)
  
  
	/**
	 * 发送外呼通知请求
	 * 
	 * @param to
	 *            必选参数 被叫号码
	 * @param mediaName
	 *            可选参数 语音文件名称，格式 wav。与mediaTxt不能同时为空，不为空时mediaTxt属性失效
	 * @param mediaTxt
	 *            可选参数 文本内容，默认值为空
	 * @param displayNum
	 *            可选参数 显示的主叫号码，显示权限由服务侧控制
	 * @param playTimes
	 *            可选参数 循环播放次数，1－3次，默认播放1次
	 * @param type
	 *            可选参数 : 如果传入type=1，则播放默认语音文件
	 * @param respUrl
	 *            可选参数 外呼通知状态通知回调地址，云通讯平台将向该Url地址发送呼叫结果通知
	 * @param userData
	 *            可选参数 用户私有数据
	 * @param maxCallTime
	 *            可选参数 最大通话时长
	 * @param speed
	 *            可选参数 发音速度
	 * @param volume
	 *            可选参数 音量
	 * @param pitch
	 *            可选参数 音调
	 * @param bgsound
	 *            可选参数 背景音编号
	 * @return
	 */
  public HashMap landingCall(String to, String mediaName,String mediaTxt, String displayNum, String playTimes, int type,String respUrl,String userData, String maxCallTime, String speed,String volume, String pitch, String bgsound) 
  
   
	/**
	 * 发起语音验证码请求
	 * 
	 * @param verifyCode
	 *            必选参数 验证码内容，为数字和英文字母，不区分大小写，长度4-8位
	 * @param to
	 *            必选参数 接收号码
	 * @param displayNum
	 *            可选参数 显示主叫号码，显示权限由服务侧控制
	 * @param playTimes
	 *            可选参数 循环播放次数，1－3次，默认播放1次
	 * @param respUrl
	 *            可选参数 语音验证码状态通知回调地址，云通讯平台将向该Url地址发送呼叫结果通知
	 * @param lang
	 *            可选参数 语言类型
	 * @param userData
	 *            可选参数 第三方私有数据
	 * @return
	 */
  public HashMap voiceVerify(String verifyCode, String to,String displayNum, String playTimes, String respUrl, String lang,String userData)
  
  
  /**
   * 发起IVR外呼请求
   * @param number			必选参数		待呼叫号码，为Dial节点的属性
   * @param userdata			可选参数		用户数据，在<startservice>通知中返回，只允许填写数字字符，为Dial节点的属性	
   * @param record			可选参数		是否录音，可填项为true和false，默认值为false不录音，为Dial节点的属性
   * @return
   */
  public HashMap ivrDial(String number, String userdata, boolean record)
  
  
  /**
   * 话单下载
   * @param date			必选参数		day 代表前一天的数据（从00:00 – 23:59）
   * @param keywords    		可选参数        	 客户的查询条件，由客户自行定义并提供给云通讯平台。默认不填忽略此参数
   * @return
   */
  public HashMap billRecords(String date, String keywords) 
  
  
  	/**
	 * 短信模板查询
	 * 
	 * @param templateId
	 *            可选参数 模板Id，不带此参数查询全部可用模板
	 * @return
	 */
  public HashMap<String, Object> QuerySMSTemplate(String templateId)


	/**
	 * 取消回拨
	 * 
	 * @param callSid
	 *            必选参数 一个由32个字符组成的电话唯一标识符
	 * @param type
	 *            可选参数 0： 任意时间都可以挂断电话；1 ：被叫应答前可以挂断电话，其他时段返回错误代码；2： 主叫应答前可以挂断电话，其他时段返回错误代码；默认值为0。
	 * @return
	 */
	public HashMap<String, Object> CallCancel(String callSid, String type) 
  

 	/**
	 * 呼叫状态查询
	 * 
	 * @param callid
	 *            必选参数 呼叫Id
	 * @param action
	 *            可选参数 查询结果通知的回调url地址
	 * @return
	 */
	public HashMap<String, Object> QueryCallState(String callid, String action)
 

	/**
	 * 呼叫结果查询
	 * 
	 * @param callSid
	 *            必选参数 呼叫Id
	 * @return
	 */
	public HashMap<String, Object> CallResult(String callSid)
 
 
  	/**
	 * 语音文件上传
	 * 
	 * @param filename
	 *            必选参数 文件名
	 * @param requestbudy
	 *            必选参数 二进制数据
	 * @return
	 */
	public HashMap<String, Object> MediaFileUpload(String filename, String requestbudy)
</pre>