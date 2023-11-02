package com.wanted.teamr.tastyfinder.common;

import java.util.function.BiFunction;

/**
 * 파라미터 3개 받는 함수형 인터페이스
 * <p>
 * 기본으로 제공된 함수형 인터페이스 중 파라미터 1, 2개만 받는 것만 존재
 * <p>
 * {@link BiFunction} 참고
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {

    R apply(T t, U u, V v);

}

