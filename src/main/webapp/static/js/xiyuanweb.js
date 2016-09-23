var Xy = {
    INTERVAL_TIME: 500000,
    DEV: true
};

/**
 * 得到绝对路径
 * @param url 相对路径
 * @returns {*} 绝对路径
 */
Xy.absPath = function (url) {
    return '/xiyuanweb-charging' + url;
};

/**
 * API请求
 * @param url 请求地址
 * @param param 请求参数
 * @param callback 请求成功后回调方法，该方法有一个参数，是请求到的数据
 */
Xy.requestApi = function (url, param, callback) {
    // $.post(Xy.absPath(url), param || {}, function (resp) {
    //     if (resp.code == 0) {
    //         if (callback) {
    //             callback(resp.data);
    //         }
    //     } else {
    //         console.log('rquest error');
    //     }
    // });
    $.ajax({
        url: Xy.absPath(url),
        data: param || {},
        type: 'POST',
        timeout: 10000,
        success: function (resp) {
            if (resp.code == 0) {
                if (callback) {
                    callback(resp.data);
                }
            } else {
                console.log('rquest error');
            }
        },

    });
};


/**
 * API同步请求
 * @param url 请求地址
 * @param param 请求参数
 * @param callback 请求成功后回调方法，该方法有一个参数，是请求到的数据
 */
Xy.requestApiSync = function (url, param, callback) {
    // $.post(Xy.absPath(url), param || {}, function (resp) {
    //     if (resp.code == 0) {
    //         if (callback) {
    //             callback(resp.data);
    //         }
    //     } else {
    //         console.log('rquest error');
    //     }
    // });
    $.ajax({
        url: Xy.absPath(url),
        data: param || {},
        type: 'POST',
        async:false,
        timeout: 10000,
        success: function (resp) {
            if (resp.code == 0) {
                if (callback) {
                    callback(resp.data);
                }
            } else {
                console.log('rquest error');
            }
        },

    });
};
// demo
// $(function () {
//     Xy.requestApi('/operation/get_charge', {}, function (data) {
//         console.log(data.all_charge_amount)
//         console.log(data.all_charge_quantity)
//     })
// });