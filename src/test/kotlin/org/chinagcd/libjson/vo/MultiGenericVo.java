package org.chinagcd.libjson.vo;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
public class MultiGenericVo<A, B, C, D, E, F, G> {
    private A a;
    private B b;
    private C c;
    private D d;
    private E e ;
    // 引用泛型这种的类型
    private Map<B, E> map;
    private List<G> list;
    // 一层一层嵌套的类型
    private Set<List<Map<F, List<HashSet<G>>>>> sets;
    private List<Set<Map<String, MultiGenericVo<A, B, C, D, E, F, G>>>> list2;

    public MultiGenericVo<String, Long, Boolean, List<String>, Set<Long>, Date, Float> init() {
        MultiGenericVo<String, Long, Boolean, List<String>, Set<Long>, Date, Float> u = new MultiGenericVo<>();
        u.a = "aa";
        u.b = 1L;
        u.c = true;
        u.d = Arrays.asList("a", "b", "c");
        u.e = Set.of(1L,2L,3L,4L);
        // map 是随机的，所以可能有的时候是顺序的，有的时候顺序是乱的
        u.map = Map.of(200L, Set.of(11L,22L,33L));
        u.list = List.of(1.0f, 2.0f);
        u.sets = Set.of(
                List.of(Map.of(toDate("2021-01-12"), List.of(new HashSet<>(List.of(1.0f, 2.0f, 3.0f))))),
                List.of(Map.of(toDate("2021-01-13"), List.of(new HashSet<>(List.of(1.0f, 2.0f, 3.0f))))),
                List.of(Map.of(toDate("2021-01-14"), List.of(new HashSet<>(List.of(1.0f, 2.0f, 3.0f)))))
        );

        MultiGenericVo<String, Long, Boolean, List<String>, Set<Long>, Date, Float> u1 = new MultiGenericVo<>();
        u1.a = "bb";
        u1.b = 2L;
        u1.c = true;
        u1.d = Arrays.asList("a", "b", "c");
        u1.e = Set.of(1L,2L,3L,4L);
        u1.map = Map.of(100L, Set.of(1L,2L,3L), 200L, Set.of(11L,22L,33L));
        u1.list = List.of(1.0f, 2.0f);
        u1.sets = Set.of(
                List.of(Map.of(toDate("2021-01-12"), List.of(new HashSet<>(List.of(1.0f, 2.0f, 3.0f))))),
                List.of(Map.of(toDate("2021-01-13"), List.of(new HashSet<>(List.of(1.0f, 2.0f, 3.0f))))),
                List.of(Map.of(toDate("2021-01-14"), List.of(new HashSet<>(List.of(1.0f, 2.0f, 3.0f)))))
        );
        MultiGenericVo<String, Long, Boolean, List<String>, Set<Long>, Date, Float> u2 = new MultiGenericVo<>();
        u2.a = "cc";
        u2.b = 3L;
        u2.c = false;
        u2.d = Arrays.asList("a", "b", "c");
        u2.e = Set.of(1L,2L,3L,4L);
        u2.map = Map.of(100L, Set.of(1L,2L,3L), 200L, Set.of(11L,22L,33L));
        u2.list = List.of(1.0f, 2.0f);
        u2.sets = Set.of(
                List.of(Map.of(toDate("2021-01-12"), List.of(new HashSet<>(List.of(1.0f, 2.0f, 3.0f))))),
                List.of(Map.of(toDate("2021-01-13"), List.of(new HashSet<>(List.of(1.0f, 2.0f, 3.0f))))),
                List.of(Map.of(toDate("2021-01-14"), List.of(new HashSet<>(List.of(1.0f, 2.0f, 3.0f)))))
        );

        u.list2 = List.of(
                Set.of(Map.of("aa", u1)),
                Set.of(Map.of("bb", u2)));
        return u;
    }

    private static Date toDate(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

}
