package com.sportsmanager.ui;

public class AppStyle {

    // Backgrounds
    public static final String BG_MAIN = "#0F172A";
    public static final String BG_PANEL = "#1E293B";
    public static final String BG_CARD = "#263248";

    // Accents
    public static final String ACCENT_BLUE = "#CFE8FF";
    public static final String ACCENT_ORANGE = "#FFB38A";
    public static final String ACCENT_GREEN = "#A8E6CF";
    public static final String ACCENT_CORAL = "#FFB3B3";

    // Text
    public static final String TEXT_PRIMARY = "white";
    public static final String TEXT_SECONDARY = "#94A3B8";
    public static final String TEXT_ACCENT = "#CFE8FF";

    // Button styles
    public static final String BTN_NORMAL =
            "-fx-background-color: #1E293B; " +
            "-fx-text-fill: #CFE8FF; " +
            "-fx-border-color: #CFE8FF; " +
            "-fx-border-width: 1px; " +
            "-fx-cursor: hand;";

    public static final String BTN_HOVER =
            "-fx-background-color: #263248; " +
            "-fx-text-fill: #CFE8FF; " +
            "-fx-border-color: #CFE8FF; " +
            "-fx-border-width: 1px; " +
            "-fx-cursor: hand;";

    public static final String BTN_ACTIVE =
            "-fx-background-color: #FFB38A; " +
            "-fx-text-fill: #0F172A; " +
            "-fx-font-weight: bold; " +
            "-fx-border-color: #FFD4B3; " +
            "-fx-border-width: 2px; " +
            "-fx-effect: dropshadow(gaussian, #FFB38A, 10, 0.4, 0, 0); " +
            "-fx-cursor: hand;";

    public static final String BTN_PRIMARY =
            "-fx-background-color: #CFE8FF; " +
            "-fx-text-fill: #0F172A; " +
            "-fx-font-weight: bold; " +
            "-fx-border-color: #CFE8FF; " +
            "-fx-border-width: 1px; " +
            "-fx-cursor: hand;";

    public static final String BTN_PRIMARY_HOVER =
            "-fx-background-color: #B8DAFF; " +
            "-fx-text-fill: #0F172A; " +
            "-fx-font-weight: bold; " +
            "-fx-border-color: #B8DAFF; " +
            "-fx-border-width: 1px; " +
            "-fx-cursor: hand;";

    public static final String BTN_SELECTED =
            "-fx-background-color: #FFB38A; " +
            "-fx-text-fill: #0F172A; " +
            "-fx-font-weight: bold; " +
            "-fx-border-color: #FFD4B3; " +
            "-fx-border-width: 2px; " +
            "-fx-effect: dropshadow(gaussian, #FFB38A, 12, 0.5, 0, 0); " +
            "-fx-cursor: hand;";

    public static final String BTN_BACK =
            "-fx-background-color: #FFB38A; " +
            "-fx-text-fill: #0F172A; " +
            "-fx-font-weight: bold; " +
            "-fx-border-color: #FFB38A; " +
            "-fx-border-width: 1px; " +
            "-fx-cursor: hand;";

    // Role colors
    public static final String ROLE_STARTING = "#A8E6CF";
    public static final String ROLE_BENCH = "#CFE8FF";
    public static final String ROLE_EXCLUDED = "#FFB3B3";
    public static final String ROLE_INJURED = "#FFB38A";
}