let translationCn = {
    "The insurance policy record was not found, please try again.": "保单记录未找到，请重试。",
    "The insured individual in policy record has a different name, please try again.": "保单中被保险人的姓名与输入不一致，请重试。"
};

function _T(msg) {
    if(app_lang !== "zh-CN")
        return msg;
    return translationCn[msg];
}