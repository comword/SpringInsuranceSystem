let translationCn = {
    "Empty username or password.": "用户名或密码为空。",
    "User not found.": "用户未找到。",
    "Incorrect password or username.": "用户名或密码不正确。"
};

function _T(msg) {
    if(app_lang !== "zh-CN")
        return msg;
    return translationCn[msg];
}