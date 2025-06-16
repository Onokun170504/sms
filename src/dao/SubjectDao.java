package dao;

import java.util.Objects;

import bean.School;

public class SubjectDao {
    private String cd; // 科目コード
    private String name; // 科目名
    private String description; // 科目の説明
    private School school; // 科目が属する学校

    public SubjectDao(String cd, String name, String description, School school) {
        this.cd = cd; // 引数で渡されたcdをインスタンス変数cdに設定
        this.name = name; // 引数で渡されたnameをインスタンス変数nameに設定
        this.description = description; // 引数で渡されたdescriptionをインスタンス変数descriptionに設定
        this.school = school; // 引数で渡されたschoolをインスタンス変数schoolに設定
    }

    //-------------------------------------------------------------------------

    /**
     * 科目コードを取得
     * @return 科目コード*/
    public String getCd() {
        return cd;
    }

    /**
     * 科目名を取得します。
     * @return 科目名*/
    public String getName() {
        return name;
    }

    /**
     * 科目の説明を取得します。
     * @return 科目の説明*/
    public String getDescription() {
        return description;
    }

    /**
     * 科目が属する学校を取得します。
     * @return Schoolオブジェクト
     */
    public School getSchool() {
        return school;
    }

    //-------------------------------------------------------------------------

    /**
     * 科目コードを設定します。
     * @param cd 設定する科目コード
     */
    public void setCd(String cd) {
        this.cd = cd;
    }

    /**
     * 科目名を設定します。
     * @param name 設定する科目名*/
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 科目の説明を設定します。
     * @param description 設定する科目の説明 */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 科目が属する学校を設定します。
     * @param school 設定するSchoolオブジェクト*/
    public void setSchool(School school) {
        this.school = school;
    }

    //-------------------------------------------------------------------------

    @Override // 親クラス(Object)のtoStringメソッドをオーバーライドする
    public String toString() {
        return "Subject{" +
                "cd='" + cd + '\'' + // 科目コード
                ", name='" + name + '\'' + // 科目名
                ", description='" + description + '\'' + // 科目の説明
                ", school=" + (school != null ? school.getName() : "null") + '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDao subject = (SubjectDao) o;

        return Objects.equals(cd, subject.cd) && Objects.equals(school, subject.school);
    }

  //-------------------------------------------------------------------------

    @Override // 親クラス (Object) のhashCodeメソッドをオーバーライドする
    public int hashCode() {
        return Objects.hash(cd, school);
    }
}