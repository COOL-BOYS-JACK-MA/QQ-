module.exports = {
    stack_1, get, URI, getType, _DJB
}

function stack_1(e) {
    e = URI(e);
    var t;
    if (e) {
        if (e.host && e.host.indexOf("qzone.qq.com") > 0) {
            try {
                t = get("p_skey")
            } catch (e) {
                t = get("p_skey")
            }
        } else if (e.host && e.host.indexOf("qq.com") > 0) {
            t = get("skey")
        }
    }
    if (!t) {
        t = get("p_skey") || get("skey") || get("rv2") || ""
    }
    return _DJB(t)
}

function get(e) {
    var cookie = ""
    var t = new RegExp("(?:^|;+|\\s+)" + e + "=([^;]*)")
    var n = cookie.match(t);
    console.log(n);
    return !n ? "" : n[1]
}


function URI(e) {
    if (!(getType(e) == "string")) {
        return null
    }
    if (e.indexOf("//") == 0) {
        e = window.location.protocol + e
    }
    if (e.indexOf("://") < 1) {
        e = location.protocol + "//" + location.host + (e.indexOf("/") == 0 ? "" : location.pathname.substr(0, location.pathname.lastIndexOf("/") + 1)) + e
    }
    var t = e.split("://");
    if (getType(t) == "array" && t.length > 1 && /^[a-zA-Z]+$/.test(t[0])) {
        this.protocol = t[0].toLowerCase();
        var n = t[1].split("/");
        if (getType(n) == "array") {
            this.host = n[0];
            this.pathname = "/" + n.slice(1).join("/").replace(/(\?|\#).+/i, "");
            this.href = e;
            var r = t[1].lastIndexOf("?")
                , i = t[1].lastIndexOf("#");
            this.search = r >= 0 ? t[1].substring(r) : "";
            this.hash = i >= 0 ? t[1].substring(i) : "";
            if (this.search.length > 0 && this.hash.length > 0) {
                if (i < r) {
                    this.search = ""
                } else {
                    this.search = t[1].substring(r, i)
                }
            }
            return this
        } else {
            return null
        }
    } else {
        return null
    }
}


function getType(e) {
    return e === null ? "null" : e === undefined ? "undefined" : Object.prototype.toString.call(e).slice(8, -1).toLowerCase()
}


function _DJB(e) {
    var t = 5381;
    for (var n = 0, r = e.length; n < r; ++n) {
        t += (t << 5) + e.charCodeAt(n)
    }
    return t & 2147483647
}

function add(a, b) {
    return a + b;
}
