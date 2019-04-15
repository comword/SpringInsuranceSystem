let translationCn = {
    "Username already exists.": "用户名已经存在。",
    "Email already exists.": "电子邮件已经存在。",
    "Session token expired, please refresh the page.": "会话令牌已经过期，请刷新页面。",
    "Could not obtain basic info from session, please restart registration.": "无法获得会话注册基本信息，请重新开始注册。",
    "Your ID number or phone number is already been registered, please recover your previous account or contact us.": "您的身份证或手机号已经被注册，请找回您的账户或者联系我们。"
};

function _T(msg) {
    if(app_lang !== "zh-CN")
        return msg;
    return translationCn[msg];
}