package repository;

import model.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class MemberRepository {
    private List<Member> memberList;

    public MemberRepository() {
        this.memberList = new ArrayList<>();
    }

    public void addMember(Member member) {
        this.memberList.add(member);
    }

    public List<Member> getAllMembers() {
        return Collections.unmodifiableList(memberList);
    }

    public Optional<Member> getMemberById(int id) {
        return memberList.stream().filter(member -> member.getId() == id).findFirst();
    }

    public void clearAll() {
        this.memberList.clear();
    }
}
