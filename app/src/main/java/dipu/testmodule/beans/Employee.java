package dipu.testmodule.beans;

public class Employee  {

    private String id;
    private String name;
    private String address;
    private String age;
    private String gendar;
    private String designatin;
    private String test_status;
    private String attempt_question;
    private String marks;
    private String test_time;
    private String area_manager_id;
    private String area;

    public String getArea_manager_id() {
        return area_manager_id;
    }

    public void setArea_manager_id(String area_manager_id) {
        this.area_manager_id = area_manager_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGendar() {
        return gendar;
    }

    public void setGendar(String gendar) {
        this.gendar = gendar;
    }

    public String getDesignatin() {
        return designatin;
    }

    public void setDesignatin(String designatin) {
        this.designatin = designatin;
    }

    public String getTest_status() {
        return test_status;
    }

    public void setTest_status(String test_status) {
        this.test_status = test_status;
    }

    public String getAttempt_question() {
        return attempt_question;
    }

    public void setAttempt_question(String attempt_question) {
        this.attempt_question = attempt_question;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getTest_time() {
        return test_time;
    }

    public void setTest_time(String test_time) {
        this.test_time = test_time;
    }
}