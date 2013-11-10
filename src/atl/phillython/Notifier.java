package atl.phillython;

import android.app.Notification;

public class Notifier {
	@SuppressWarnings("deprecation")
	public Notification createPersistentNotification() {
		if (android.os.Build.VERSION.SDK_INT > 15)
			return new Notification.Builder(ContextProvider.getContext())
					.setContentTitle("EducaTour is Running.")
					.setContentText("You will recieve notifications live.")
					.setSmallIcon(R.drawable.notification)
					.setOngoing(true)
					.build();
		return new Notification.Builder(ContextProvider.getContext())
				.setContentTitle("EducaTour is Running.")
				.setContentText("You will recieve notifications live.")
				.setSmallIcon(R.drawable.notification)
				.setOngoing(true)
				.getNotification();
	}
	
	public Notification createNotification(String title, String description) {
		if (android.os.Build.VERSION.SDK_INT > 15)
			return new Notification.Builder(ContextProvider.getContext())
					.setContentTitle(title)
					.setContentText(description)
					.setSmallIcon(R.drawable.loc_notif)
					.build();
		return new Notification.Builder(ContextProvider.getContext())
				.setContentTitle(title)
				.setContentText(description)
				.setSmallIcon(R.drawable.loc_notif)
				.getNotification();
	}
}
