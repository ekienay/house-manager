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
public class Person {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "FLP")
    private String flp;
    @Column (name = "age")
    private int age;

    @Override
    public String toString() {
        return flp;
    }

}
