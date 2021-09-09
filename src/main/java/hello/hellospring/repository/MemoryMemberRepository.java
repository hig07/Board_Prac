package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements  MemberRepository{
    //static은 인스턴스와 상관없이 클래스에 붙은 것
    //static이 없었으면 service와 이 테스트 클래스에서 문제가 생김(저장되는 DB가 달라짐)
    private  static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {  //save 하기전에 이미 name은 넘어온 상태
        member.setId(++sequence);  //store에 넣기 전에 멤버의 id 값을 세팅
        store.put(member.getId(), member);  //그리고 store에 저장 -> Map에 저장됨
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));  //ofNullable : store.get(id)가 널이어도 감싸기 가능
    }

    @Override
    public Optional<Member> findByName(String name) {  //Map에서 value를 하나 찾으면 바로 반환 / 없으면 null
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());  //store.values()가 member
    }

    public void clearStore() {
        store.clear();
    }
}
