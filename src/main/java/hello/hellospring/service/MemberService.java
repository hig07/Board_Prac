package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    /*
        memberRepository와 new MemoryMemberRepository()는 new에서 다른 객체
        다른 인스턴스라고 볼 수 있음 -> 내용물이 달라질 수 있음
        아무래도 같은 것을 쓰는게 좋음
        여기의 MemoryMemberRepository와 테스트의 MemoryMemberRepository는 다른 인스턴스
         */
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
    //외부에서 memberRepository를 넣어줌 -> DI(의존 주입)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    /*
    회원가입
     */
    public Long join(Member member){
        //같은 이름이 있는 중복 회원 불가
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        Optional 팁으로 옵셔널을 바로 반환하는 것은 안좋음*/
        validateDuplicateMember(member);  //중복 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /*
    전체 회원 조회
     */
    public List<Member> findMembers(){
        return  memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
