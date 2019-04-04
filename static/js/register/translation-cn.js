let translationCn = {
    "Username already exists.": "用户名已经存在。",
    "Email already exists.": "电子邮件已经存在。",
    "Session token expired, please refresh the page.": "会话令牌已经过期，请刷新页面。",
    "Could not obtain basic info from session, please restart registration.": "无法获得会话注册基本信息，请重新开始注册。",
};

function _T(msg) {
    if(app_lang !== "zh-CN")
        return msg;
    return translationCn[msg];
}