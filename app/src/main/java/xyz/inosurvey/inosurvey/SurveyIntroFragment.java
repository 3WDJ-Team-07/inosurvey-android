package xyz.inosurvey.inosurvey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SurveyIntroFragment extends Fragment {

    private String title, contents;
    private TextView titleTextView, contentsTextView;
    private Button startButton;

    public SurveyIntroFragment(){}

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_survey_intro,container,false);

        Bundle args = this.getArguments();
        if (args != null) {
            title = args.getString("title");
            contents = args.getString("contents");
        }

        titleTextView = v.findViewById(R.id.titleTextView);
        contentsTextView = v.findViewById(R.id.contentsTextView);

        return v;
    }
}
