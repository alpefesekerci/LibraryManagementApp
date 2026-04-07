package repository;

import model.Member;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private List<Member> memberList;

    public MemberRepository() {
        this.memberList = new ArrayList<>();
    }

    public void addMember(Member member) {
        this.memberList.add(member);
    }

    public List<Member> getAllMembers() {
        return memberList;
    }

    public Member getMemberByMembershipNumber(String membershipNumber) {
        for (Member member : memberList) {
            if (member.getMembershipNumber().equals(membershipNumber)) {
                return member;
            }
        }
        return null;
    }
}
