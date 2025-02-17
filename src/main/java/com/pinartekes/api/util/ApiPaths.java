package com.pinartekes.api.util;

public class ApiPaths {
    private static final String BASE_PATH = "/api";

    public static final class ProjectCtrl {
        public static final String CTRL = BASE_PATH + "/projects";
    }

    public static final class WorkitemCtrl {
        public static final String CTRL = BASE_PATH + "/workitems";
    }

    public static final class TaskitemCtrl {
        public static final String CTRL = BASE_PATH + "/taskitems";
    }

    public static final class AuthCtrl {
        public static final String CTRL = BASE_PATH + "/auth";
    }

    public static final class UserCtrl {
        public static final String CTRL = BASE_PATH + "/users";
    }

}
