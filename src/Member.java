import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Member{
    private static Map<String, MemberInfo> members;

    public Member() {
        members = new HashMap<>();

    }
    //생성
    public void createMember(String name, String password, String phoneNum, String address, int userCase) {
        MemberInfo member = new MemberInfo(name, password, phoneNum, address, userCase);

        members.put(name, member);
        System.out.println("hashmap 정보:" + members);
        System.out.println("회원이 생성되었습니다.");
        saveMembersToFile(); // 회원 정보를 파일에 저장

    }
    //조회
    public MemberInfo readMember(String name, String password) {
        MemberInfo member = members.get(name);

        if (member != null && (member.getPassword().equals(password))) {
            System.out.println(member.toString());
        } else {
            System.out.println("해당 회원을 찾을 수 없습니다.");
        }
        return member;
    }
    //수정
    public void updateMember(int num, String name) {
        MemberInfo member = members.get(name);
        Scanner scanner = new Scanner(System.in);
        if (member != null) {
            switch (num){
                case 1:
                    System.out.print("수정할 ID 입력:");
                    String updateName = scanner.nextLine();
                    member.setName(updateName);
                    System.out.println("회원 정보가 업데이트되었습니다.");
                    saveMembersToFile(); // 회원 정보를 파일에 저장
                case 2:
                    System.out.print("수정할 비밀번호 입력:");
                    String updatePw = scanner.nextLine();
                    member.setName(updatePw);
                    System.out.println("회원 정보가 업데이트되었습니다.");
                    saveMembersToFile(); // 회원 정보를 파일에 저장
                case 3:
                    System.out.print("수정할 전화번호 입력:");
                    String updatePhoneNum = scanner.nextLine();
                    member.setName(updatePhoneNum);
                    System.out.println("회원 정보가 업데이트되었습니다.");
                    saveMembersToFile(); // 회원 정보를 파일에 저장
                case 4:
                    System.out.print("수정할 주소 입력:");
                    String updateAddress = scanner.nextLine();
                    member.setName(updateAddress);
                    System.out.println("회원 정보가 업데이트되었습니다.");
                    saveMembersToFile(); // 회원 정보를 파일에 저장
            }
        } else {
            System.out.println("해당 회원을 찾을 수 없습니다.");
        }
    }


    //삭제
    public void deleteMember(String name) {
        MemberInfo member = members.get(name);
        if (member != null) {
            members.remove(name);
            System.out.println("탈퇴되었습니다.");
            saveMembersToFile(); // 회원 정보를 파일에 저장
        } else {
            System.out.println("해당 회원을 찾을 수 없습니다.");
        }
    }
    //저장된 회원 목록 파일에서 회원들 불러오기
    public Map<String, MemberInfo> loadMembersFromFile() {
        File file = new File("C:\\data\\members.csv");
        if(!file.exists()){
            try{
                if(file.createNewFile()){
                    System.out.println("파일을 생성하였습니다.");
                }
                else {
                    System.out.println("파일 생성 실패");
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\data\\members.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                String name = fields[0];
                String password = fields[1];
                String phoneNum = fields[2];
                String address = fields[3];
                int userCase = Integer.parseInt(fields[4]);
                MemberInfo member = new MemberInfo(name, password, phoneNum, address, userCase);
                members.put(name, member);

            }
        } catch (IOException e) {
            System.out.println("회원 정보 파일을 읽어오는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return members;
    }

    public void saveMembersToFile() {
        File file = new File("C:\\data\\members.csv");
        if(!file.exists()){
            try{
                if(file.createNewFile()){
                    System.out.println("파일을 생성하였습니다.");
                }
                else {
                    System.out.println("파일 생성 실패");
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (MemberInfo member : members.values()) {
                String line = member.getName() + "," + member.getPassword() + "," + member.getPhoneNum()+"," + member.getAddress() + ","+member.getUserCase();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("회원 정보를 파일에 저장하는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    public void main(Scanner scanner) {
        Member memberCRUD = new Member();

        // 회원 정보 파일로부터 읽어오기
        members = memberCRUD.loadMembersFromFile();
        System.out.println("파일로드");

        System.out.println("=== 회원 관리 시스템 ===");
        System.out.println("1. 회원 생성");
        System.out.println("2. 회원 조회");
        System.out.println("3. 회원 수정");
        System.out.println("4. 회원 탈퇴");
        System.out.println("0. 종료");

        int choice = -1;
        while (choice != 0) {
            System.out.print("원하는 작업을 선택하세요: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기

                switch (choice) {
                    case 1:
                        boolean t = true;
                        String name = null;
                        while (t) {
                            System.out.print("회원 ID: ");
                            name = scanner.nextLine();
                            if (!members.containsKey(name)) {
                                System.out.println("가능한 ID입니다.");
                                t = false;
                                break;
                            } else {
                                System.out.println("이미 존재하는 ID입니다.");
                            }
                        }
                        System.out.print("회원 비밀번호: ");
                        String password = scanner.nextLine();
                        System.out.print("회원 전화번호: ");
                        String phoneNum = scanner.nextLine();
                        System.out.print("회원 주소: ");
                        String address = scanner.nextLine();
                        memberCRUD.createMember(name, password, phoneNum, address, 2);
                        break;
                    case 2:
                        System.out.print("조회할 회원 ID: ");
                        String searchName = scanner.nextLine();
                        System.out.print("비밀번호를 입력해주세요: ");
                        String passWord = scanner.nextLine();
                        try{
                            memberCRUD.readMember(searchName, passWord);
                        }catch (NullPointerException e){
                            e.printStackTrace();
                            System.out.println("조회할 회원 파일이 비어있습니다.");
                        }
                        break;
                    case 3:
                        if(members == null){
                            System.out.println("현재 회원 목록 파일이 비어있습니다.");
                            break;
                        }
                        System.out.println("기존 회원 ID: ");
                        String beforeName = scanner.nextLine();
                        if (members.containsKey(beforeName)) {
                            System.out.println("수정할 정보를 선택해 주세요: 1. ID 2. 비밀번호 3. 전화번호 4. 주소");
                            int num = scanner.nextInt();
                            memberCRUD.updateMember(num, beforeName);
                            break;
                        }
                        else{
                            System.out.println("해당 ID가 존재하지 않습니다.");
                        }

                    case 4:
                        if(members == null){
                            System.out.println("현재 회원 목록 파일이 비어있습니다.");
                            break;
                        }
                        System.out.print("삭제할 회원 ID: ");
                        String delName = scanner.nextLine();
                        if (members.containsKey(delName)) {
                            memberCRUD.deleteMember(delName);
                            break;
                        }
                        else{
                            System.out.println("해당 ID가 존재하지 않습니다.");
                        }
                        //choice = 0;
                        break;
                    case 0:
                        System.out.println("프로그램을 종료합니다.");
                        choice = 0;
                        break;
                    default:
                        System.out.println("올바른 메뉴를 선택하세요.");
                }
            }catch(InputMismatchException ime){
                scanner = new Scanner(System.in);
                System.out.println("잘못된 값이 입력되었습니다. 다시 입력해주세요");
//                ime.printStackTrace();
            }
        }
        //scanner.close();
        return;
    }
}

class MemberInfo {

    private String name;
    private String password;
    private String phoneNum;
    private String address;
    private int userCase;

    public MemberInfo(String name, String password, String phoneNum, String address, int userCase) {
        this.name = name;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
        this.userCase= userCase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserCase() {
        return userCase;
    }

    @Override
    public String toString() {
        return "ID: " + name + ", PW: " + password +", 전화번호: " + phoneNum + ", 주소: " + address + ", 유형: " + userCase;
    }
}
