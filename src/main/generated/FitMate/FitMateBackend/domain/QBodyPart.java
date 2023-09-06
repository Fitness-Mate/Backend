package FitMate.FitMateBackend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBodyPart is a Querydsl query type for BodyPart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBodyPart extends EntityPathBase<BodyPart> {

    private static final long serialVersionUID = 2106091079L;

    public static final QBodyPart bodyPart = new QBodyPart("bodyPart");

    public final StringPath englishName = createString("englishName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath koreanName = createString("koreanName");

    public final ListPath<Machine, QMachine> machines = this.<Machine, QMachine>createList("machines", Machine.class, QMachine.class, PathInits.DIRECT2);

    public final ListPath<Workout, QWorkout> workouts = this.<Workout, QWorkout>createList("workouts", Workout.class, QWorkout.class, PathInits.DIRECT2);

    public QBodyPart(String variable) {
        super(BodyPart.class, forVariable(variable));
    }

    public QBodyPart(Path<? extends BodyPart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBodyPart(PathMetadata metadata) {
        super(BodyPart.class, metadata);
    }

}

