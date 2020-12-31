var baiDuFanYi = require('./method')   // 目录名/文件名
var express = require('express')
var bodyParser = require('body-parser')
var app = express()





app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

app.post('/add', function (req, res) {
    var body = req.body
    console.log(baiDuFanYi.stack_1(body.e))
    res.send(baiDuFanYi.stack_1(body.e)+"")
    res.end()
})

app.post('/test', function (req, res) {

    var body = req.body
    console.log('接受到请求')
    baiDuFanYi.do1()
    res.end()
})

app.listen(8083, () => {
    console.log(8083 + "端口开启")
});

// 后端测试用例
/*
@Test
public void test_1() throws Exception {
    String uri = "http://localhost:8083/add";
    Connection connect = Jsoup.connect(uri);
    HashMap<String, String> map = new HashMap<>();
    map.put("v1", "2");
    map.put("v2", "3");
    connect.data(map);
    Document post = connect.ignoreContentType(true).post();
    System.out.println(post);
    System.out.println("成功");
}
*/

