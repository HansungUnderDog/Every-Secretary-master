package cse.underdog.org.underdog_client;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

import java.util.Calendar;

public class GetEvent {
    Cursor cursor = null;
    ContentResolver cr;
    Uri uri;

    static final String[] CALENDARS_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,           // 0
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,  // 1
            CalendarContract.Calendars.OWNER_ACCOUNT, // 2
            CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, // 3
            CalendarContract.Calendars.ACCOUNT_NAME, // 4
            CalendarContract.Calendars.ACCOUNT_TYPE  // 5
    };

    private static final String[] EVENT_PROJECTION = new String[] {
            Events._ID,                  // 0  do not remove; used in DeleteEventHelper
            Events.TITLE,                // 1  do not remove; used in DeleteEventHelper
            Events.RRULE,                // 2  do not remove; used in DeleteEventHelper
            Events.ALL_DAY,              // 3  do not remove; used in DeleteEventHelper
            Events.CALENDAR_ID,          // 4  do not remove; used in DeleteEventHelper
            Events.DTSTART,              // 5  do not remove; used in DeleteEventHelper
            Events._SYNC_ID,             // 6  do not remove; used in DeleteEventHelper
            Events.EVENT_TIMEZONE,       // 7  do not remove; used in DeleteEventHelper
            Events.DESCRIPTION,          // 8
            Events.EVENT_LOCATION,       // 9
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, // 10
            Events.CALENDAR_COLOR,       // 11
            Events.EVENT_COLOR,          // 12
            Events.HAS_ATTENDEE_DATA,    // 13
            Events.ORGANIZER,            // 14
            Events.HAS_ALARM,            // 15
            CalendarContract.Calendars.MAX_REMINDERS,     // 16
            CalendarContract.Calendars.ALLOWED_REMINDERS, // 17
            Events.CUSTOM_APP_PACKAGE,   // 18
            Events.CUSTOM_APP_URI,       // 19
            Events.DTEND,                // 20
            Events.DURATION,             // 21
            Events.ORIGINAL_SYNC_ID      // 22 do not remove; used in DeleteEventHelper
    };

    public GetEvent(ContentResolver cr) {
        this.cr = cr;
        uri = CalendarContract.Calendars.CONTENT_URI;

 //       cursor = cr.query(uri, EVENT_PROJECTION, );
    }

    public void handleComplete() {

    }
}
