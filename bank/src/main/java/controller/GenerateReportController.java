package controller;

import database.Constants;
import model.Activity;
import model.validation.Notification;
import service.user.UserActivityService;
import view.GenerateReportView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

import static database.Constants.Roles.ADMINISTRATOR;

public class GenerateReportController extends Controller{

    private final GenerateReportView reportView;
    private final TableProcessing tableProcessing;

    public GenerateReportController(UserActivityService activityService, GenerateReportView reportView,
                                    TableProcessing tableProcessing) {
        super(activityService);
        this.reportView = reportView;
        this.tableProcessing = tableProcessing;
        reportView.setGenerateButtonActionListener(new ConfirmListener());
        List<Activity> activities = new ArrayList<>();
        reportView.loadTable(tableProcessing.generateTable(activities, Constants.Columns.ACTIVITY_TABLE_COLUMNS));
    }

    @Override
    public void setVisibility(Boolean bool) {
        reportView.setVisible(bool);
    }

    private class ConfirmListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = reportView.getUsername();
            try {
                Date startDate = (new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(reportView.getStartDate()));
                Date endDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(reportView.getEndDate());
                Notification<List<Activity>> activitiesNotification = getActivityService().findByDateAndUser(username, startDate, endDate);
                if(activitiesNotification.hasErrors()) {
                    reportView.showMessage(activitiesNotification.getFormattedErrors());
                } else {
                    List<Activity> activities = activitiesNotification.getResult();
                    reportView.loadTable(tableProcessing.generateTable(activities, Constants.Columns.ACTIVITY_TABLE_COLUMNS));
                    registerActivity(getLoggedInUser(), new Date(), "Generated report for user " + username, ADMINISTRATOR);
                }
            } catch(Exception exc) {
                reportView.showMessage("Incorrect date format! \n Proper date format example: March 3, 2004");
            }
        }
    }

}
