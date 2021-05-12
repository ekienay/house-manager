package entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Setter
@Table (name = "person")
public class Person{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "FLP")
    private String flp;
    @Column (name = "age")
    private int age;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    public Passport pass(){
        return passport;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", flp='" + flp + '\'' +
                ", age=" + age +
                '}';
    }
}
