package com.github.syominsergey.spent_resources_analyzer.sources;

import java.io.IOException;

/**
 * Created by Sergey on 28.06.2017.
 */
public interface ActivityReader {
    boolean readNextActivity(Activity activity) throws IOException;
}
