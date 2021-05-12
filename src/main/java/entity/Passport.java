package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "passport")
public class Passport  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "series")
    private String series;
    @Column(name = "number")
    private int number;

    @OneToOne(mappedBy = "passport")
    private Person person;

    @Override
    public String toString() {
        return series + " " + number;
    }
}
