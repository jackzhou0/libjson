package org.chinagcd.libjson

import org.chinagcd.libjson.vo.*
import org.junit.jupiter.api.Test
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.script.ScriptEngineManager

class SerializerTest {

    val engine = ScriptEngineManager().getEngineByName("nashorn")


    @Test
    fun testNullOrEmpty() {
        // null or emtpy
        localEquals(null)
        localEquals("")
//        // array
        localEquals(arrayOf<Any>())
        localEquals(ByteArray(0))
        localEquals(ShortArray(0))
        localEquals(IntArray(0))
        localEquals(FloatArray(0))
        localEquals(DoubleArray(0))
        localEquals(LongArray(0))
        localEquals(BooleanArray(0))
        localEquals(CharArray(0))

        // list
        localEquals(listOf<Any>())
        localEquals(arrayListOf<Any>())
        localEquals(LinkedList<Any>())

        // set
        localEquals(setOf<Any>())
        localEquals(hashSetOf<Any>())
        localEquals(linkedSetOf<Any>())
        localEquals(TreeSet<Any>())

        // map
        localEquals(mapOf<Any, Any>())
        localEquals(linkedMapOf<Any, Any>())
        localEquals(ConcurrentHashMap<Any, Any>())
        localEquals(TreeMap<Any, Any>())
        localEquals(Hashtable<Any, Any>())
        localEquals(Properties())
    }

    @Test
    fun testNum() {
        localEquals(1.toByte())
        localEquals(2)
        localEquals(3.toShort())
        localEquals(4.0.toFloat())
        localEquals(5.toLong())
        localEquals(1.0)
        localEquals(97.toChar())
        localEquals(true)
        localEquals(false)
        localEquals(BigDecimal("10123412312312312312312300.12"))
        localEquals(BigDecimal("10123412312312312312312300.0"))
        localEquals(BigDecimal("10123123123123123123123123123123123123123123122312300.3"))
        localEquals(BigDecimal("10123123123123123123123123123123123123123123122312300.0"))
        localEquals(BigInteger("10123123123123123123123123123123123123123123122312300"))
    }

    @Test
    fun testArray() {
        localEquals(byteArrayOf(1, 2, 3))
        localEquals(shortArrayOf(1, 2, 3))
        localEquals(intArrayOf(1, 2, 3))
        localEquals(floatArrayOf(1.0f, 2.0f, 3.0f))
        localEquals(charArrayOf('a', 'b', 'c'))
        localEquals(doubleArrayOf(1.0, 2.0, 3.0))
        localEquals(longArrayOf(1, 2, 3))
        localEquals(booleanArrayOf(true, false, true))
    }

    @Test
    fun testList() {
        localEquals(listOf<Any>(1, 2, 3))
        localEquals(arrayListOf<Any>(1, 2, 3))
        localEquals(LinkedList<Any>(listOf(1, 2, 3)))


        val list = ArrayList<ArrayList<ArrayList<String>>>()
        val aa = ArrayList<ArrayList<String>>()
        val bb = ArrayList<String>()
        bb.add("aaa")
        bb.add("bbb")
        bb.add("ccc")
        aa.add(bb)
        list.add(aa)
        val cc = ArrayList<String>()
        cc.add("aaa")
        cc.add("bbb")
        cc.add("ccc")
        aa.add(cc)
        list.add(aa)
        localEquals(list)

        // 不带范型的集合
        val noGenericList = ArrayList<Any?>()
        noGenericList.add("123")
        noGenericList.add(222)
        noGenericList.add("2021-02-12 12:22:22")
        noGenericList.add(list)
        noGenericList.add(mapOf(Pair("aa", "123"), Pair("bb", "456")))
        noGenericList.add(BigDecimal(123.33))

        val map = mapOf(
            Pair("id", "123"),
            Pair("data", listOf(1, 2, 3, arrayOf(4, 5, 6))),
            Pair(
                "a1", mapOf(
                    Pair("aa", "haha"),
                    Pair("bb", listOf("aa", "bb", "cc", mapOf(Pair("a1", "a1"), Pair("a2", "a2")))),
                    Pair("cc", mapOf(Pair("c1", "cc"), Pair("c2", "c2"), Pair("c3", "c3")))
                )
            ),
            Pair("list", listOf(11, 22, 33))
        )

        val data = arrayOf(
            "haha",
            "hihi",
            null,
            map,
            listOf(
                1,
                2,
                3, map,
                listOf(
                    1.0, 2.0, 3.0, arrayOf(
                        "aa", "bb", "cc", arrayOf(1, 2, 3),
                        setOf(1, 2, 3, hashMapOf(Pair("aa", "aa"), Pair("bb", "asdf")))
                    )
                ),
                arrayOf(
                    4,
                    5, map,
                    listOf(
                        7,
                        8,
                        9, map,
                        byteArrayOf(12, 3),
                        shortArrayOf(1, 2, 3), map,
                        booleanArrayOf(true, false, true, true, true, false)
                    )
                )
            ),
            arrayOf(10, 11, arrayOf(13, 14, Hashtable<Any, Any>()), listOf<Int>())
        )
        noGenericList.add(data)
        localEquals(noGenericList)
    }

    @Test
    fun testSet() {
        localEquals(setOf<Any>(1, 2, 3))
        localEquals(linkedSetOf<Any>(1, 2, 3))
        localEquals(TreeSet<Any>(listOf(1, 2, 3)))
    }

    @Test
    fun testMap() {
        val p1 = Pair("name", "xijinping")
        val p2 = Pair("age", 22)
        val p3 = Pair("birth", SimpleDateFormat("yyyy-MM-dd").parse("2022-03-21"))
        val p4 = Pair("active", true)
        val p5 = Pair("sex", 'm')
        val p6 = Pair("addrs", listOf("北京", "上海", "南京", "广州", "深圳", "西安"))
        val p7 = Pair("basic", mapOf(Pair("id", 1), Pair("label", "root")))
        val p8 = Pair("created_time", LocalDate.of(2022, 3, 21))
        val p9 = Pair("login_time", LocalDateTime.of(2022, 3, 21, 14, 21, 0))

        val root = TreeVo(1, "aa1")

        val e1 = TreeVo(2, "aa2")
        val e11 = TreeVo(3, null)
        val e12 = TreeVo(4, "aa4")
        val e111 = TreeVo(5, "aa5")
        val e121 = TreeVo(6, "aa6")
        val e1211 = TreeVo(7, "aa7")
        val e13 = TreeVo(8, "aa8")
        val e131 = TreeVo(9, "aa9")
        val e1311 = TreeVo(10, "aa10")
        val e1312 = TreeVo(11, "aa11")
        val e13121 = TreeVo(12, "aa12")
        val e13122 = TreeVo(13, "aa13")
        val e131221 = TreeVo(14, "aa14")
        val e131222 = TreeVo(15, "aa15")
        val e1312221 = TreeVo(16, "aa16")
        root.children = arrayListOf(e1)
        e1.children = arrayListOf(e11, e12, e13)
        e11.children = arrayListOf(e111)
        e12.children = arrayListOf(e121)
        e121.children = arrayListOf(e1211)
        e13.children = arrayListOf(e131)
        e131.children = arrayListOf(e1311, e1312)
        e1312.children = arrayListOf(e13121, e13122)
        e13122.children = arrayListOf(e131221, e131222)
        e131222.children = arrayListOf(e1312221)

        val annotationVo = AnnotationVo()
        annotationVo.id = 1
        annotationVo.addr = true
        annotationVo.sex = 11
        val nestedMap = mapOf(Pair("aa", "aa"), Pair("bb", null), Pair("cc", "cc"), Pair("obj", annotationVo))

        val user = User()
        user.id = 1
        val nonGenericMap = HashMap<Any, Any>()
        nonGenericMap["name"] = "aa"
        nonGenericMap[Date()] = "haha"
        nonGenericMap[true] = "active"
        nonGenericMap[123] = "amount"
        // 测试对象作为key的场景
        nonGenericMap[user] = "user"
        // 测试html的场景
        nonGenericMap["html"] = "<html><head><title>test</title></head><div>adf</div></html>"
        var multiGenericVo = MultiGenericVo<String, Long, Boolean, List<String>, Set<Long>, Date, Float>().init();
        val html = "<html><head><title>test</title></head><div>adf</div></html>"
        val pattern = "^<[^>]+>.*<[^>]+>$|^<[^/>]+(\\\\/>|>)$"
        System.err.println(html.matches(Regex(pattern)))

        val map = mapOf(
            p1, p2, p3, p4, p5, p6, p7, Pair(
                "ca", arrayListOf<Any>(
                    hashMapOf<String, Any>(
                        Pair(
                            "aa",
                            setOf(
                                1,
                                2,
                                multiGenericVo,
                                p8,
                                p9,
                                null,
                                hashMapOf<String, Any?>(
                                    Pair(
                                        "aa1",
                                        listOf<Any>(hashMapOf<String, Any>(), hashSetOf("aa", "bb", "cc"))
                                    ),
                                    Pair("bb2", null)
                                ),
                                root,
                                multiGenericVo,
                                arrayOf(1, 2, 3),
                                byteArrayOf(1, 2, 3, 4, 5),
                                shortArrayOf(5, 6, 6),
                                intArrayOf(1, 2, 3, 4, 5),
                                floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f),
                                charArrayOf('a', 'b', 'c', 'd', 'e'),
                                doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0),
                                longArrayOf(1, 2, 3, 4, 5),
                                booleanArrayOf(true, false, true, false, true),
                                BigDecimal("10123412312312312312312300.12"),
                                BigInteger("10123123123123123123123123123123123123123123122312300"),
                                listOf<Any>(
                                    1,
                                    2,
                                    3,
                                    multiGenericVo,
                                    listOf(multiGenericVo),
                                    hashSetOf(multiGenericVo),
                                    arrayListOf(multiGenericVo)
                                ),
                                setOf<Any>(
                                    1,
                                    2,
                                    3,
                                    multiGenericVo,
                                    listOf(multiGenericVo),
                                    hashSetOf(multiGenericVo),
                                    arrayListOf(multiGenericVo, arrayOf(user))
                                ),
                                mapOf<Any, Any>(Pair("aa", multiGenericVo), Pair("bb", 2)),
                                hashMapOf<Any, Any>(
                                    Pair("aa", 1),
                                    Pair("bb", 2),
                                    Pair("haha", multiGenericVo),
                                    Pair("nested", nestedMap)
                                ),
                                hashMapOf(Pair("name", Hashtable<String, Any>()), Pair("tree", root))
                            )
                        )
                    )
                )
            )
        )
        File("/Users/XUGANG/Desktop/result.json").writeText(JSON().pretty(true).stringify(map))
        localEquals(map)

        val concurrentHashMap = ConcurrentHashMap<Any, Any>()
        concurrentHashMap.putAll(map)
        localEquals(concurrentHashMap)

        val treeMap = TreeMap<Any, Any>()
        treeMap.putAll(map)
        localEquals(treeMap)

        val hashtable = Hashtable<Any, Any>()
        hashtable.putAll(map)
        localEquals(hashtable)

        val properties = Properties()
        properties.putAll(map)
        localEquals(properties)
    }

    @Test
    fun testRecord() {
        var r = UserRecord(1, "aa", "bb")
        System.err.println(JSON().stringify(r))
    }

    @Test
    fun testMultiTimes() {
        for (i in 0..100) {
            testMap()
        }
    }

    // 测试的具体方法

    private fun localEquals(data: Any?) {
        data?.let {
            // 如果可以正常解析，则说明序列化没有问题
            val json = JSON().stringify(data)
            if (json == "" || json == "null") {
                System.err.println("null or empty string")
                return
            }
            // 只需要判断是否有效，然后判断格式化是否OK就行
            if (!valid(json)) {
                throw AssertionError("不是有效的json：$json")
            }
            if (JSON().pretty().stringify(data) != jsonPretty(json)) {
                throw AssertionError("json格式化有问题：$json")
            }
        }
    }

    private fun valid(json: String): Boolean {
        try {
            // 传过来的是json字符串，但是在js里，是json对象，所以这里需要先stringify下
            engine.eval("JSON.parse(JSON.stringify($json))")
            return true
        } catch (e: Exception) {
            System.err.println(e.message)
            return false
        }
    }

    private fun jsonPretty(json: String): String {
        return engine.eval("JSON.stringify($json, null, 4)").toString()
    }
}
