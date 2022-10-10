package hello.core.singleton;

public class SingletonService {
    // java는 static으로 선언된 변수를 프로그램이 실행될 때 자동으로 메모리에 할당해둔다.
    // 자기 자신의 객체를 static으로 선언 -> 해당 클래스의 객체를 생성하지 않고도 instance에 접근 가능.
    private static final SingletonService instance = new SingletonService();
    // 자기 자신의 인스턴스를 조회할 수 있는 유일한 방법.
    // 호출시 항상 같은, 유일한 인스턴스를 반환함.
    public static SingletonService getInstance() {
        return instance;
    }
    // 생성자가 private이므로 외부에서는 객체를 생성할 수 없게 됨.
    // new 키워드 사용해서 새로운 객체 생성할 때는 생성자가 자동으로 호출되니까....
    // 이렇게 확실하게 막아 놓는 게 예상치 못한 상황을 예방하는 가장 좋은 방법임
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
