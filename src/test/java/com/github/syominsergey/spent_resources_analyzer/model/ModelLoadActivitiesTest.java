package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Sergey on 21.07.2017.
 */
public class ModelLoadActivitiesTest {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test1() throws Exception {
        AttributeMeta<Integer> moneyMeta = new MoneyAttributeMeta();
        AttributeMeta<Integer> timeMeta = new TimeAttributeMeta();
        ArrayList<ActivityImpl> sourceActivities = new ArrayList<>();
        sourceActivities.add(new ActivityImpl(
                "1",
                Arrays.asList(
                        new AttributeImpl<>(157, moneyMeta),
                        new AttributeImpl<>(160, timeMeta)
                ),
                Arrays.asList(Arrays.asList("a"))
        ));
        sourceActivities.add(new ActivityImpl(
                "2",
                Arrays.asList(
                        new AttributeImpl<>(180, timeMeta)
                ),
                Arrays.asList(
                        Arrays.asList("a", "b"),
                        Arrays.asList("a", "c", "d")
                )
        ));
        sourceActivities.add(new ActivityImpl(
                "3",
                Arrays.asList(
                        new AttributeImpl<>(10, moneyMeta)
                ),
                Arrays.asList(
                        Arrays.asList("a", "b"),
                        Arrays.asList("a", "c", "d"),
                        Arrays.asList("a", "d", "e")
                )
        ));
        sourceActivities.add(new ActivityImpl(
                "4",
                Collections.emptyList(),
                Arrays.asList(
                        Arrays.asList("a", "c", "d")
                        , Arrays.asList("a", "d", "e", "f")
                )
        ));
        HashMap<List<String>, List<Activity>> arrangedActivities = new HashMap<>();
        arrangedActivities.put(
                Arrays.asList("a"),
                Arrays.asList(
                        new Activity(
                                "1",
                                Arrays.asList(
                                        new AttributeImpl<>(157, moneyMeta),
                                        new AttributeImpl<>(160, timeMeta)
                                )
                        )
                )
        );
        arrangedActivities.put(
                Arrays.asList("a", "b"),
                Arrays.asList(
                        new Activity(
                                "2",
                                Arrays.asList(
                                        new AttributeImpl<>(180, timeMeta)
                                )
                        ),
                        new Activity(
                                "3",
                                Arrays.asList(
                                        new AttributeImpl<>(10, moneyMeta)
                                )
                        )
                )
        );
        arrangedActivities.put(
                Arrays.asList("a", "c", "d"),
                Arrays.asList(
                        new Activity(
                                "2",
                                Arrays.asList(
                                        new AttributeImpl<>(180, timeMeta)
                                )
                        ),
                        new Activity(
                                "3",
                                Arrays.asList(
                                        new AttributeImpl<>(10, moneyMeta)
                                )
                        ),
                        new Activity(
                                "4",
                                Collections.emptyList()
                        )
                )
        );
        arrangedActivities.put(
                Arrays.asList("a", "d", "e"),
                Arrays.asList(
                        new Activity(
                                "3",
                                Arrays.asList(
                                        new AttributeImpl<>(10, moneyMeta)
                                )
                        )
                )
        );
        arrangedActivities.put(
                Arrays.asList("a", "d", "e", "f"),
                Arrays.asList(
                        new Activity(
                                "4",
                                Collections.emptyList()
                        )
                )
        );
        new ModelLoadActivitiesChecker(
                sourceActivities,
                arrangedActivities
        ).check();
    }

}
