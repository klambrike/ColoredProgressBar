package ee.klambrike.library;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class ColoredProgress extends RelativeLayout {
    private final String TAG = ColoredProgress.class.getSimpleName();
    private View rootView;
    private LinearLayout progressElementsContainer;
    private int max = 100;
    private int childrenWidth = 0;
    private List<ProgressElement> elementsList = new ArrayList<>();
    private int animationDuration = 1200;

    public ColoredProgress(Context context) {
        super(context);
        initialize(context);
    }

    public ColoredProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ColoredProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColoredProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.colored_progress, this, true);
        progressElementsContainer = rootView.findViewById(R.id.container_progress);
        View progressContainer = rootView.findViewById(R.id.container_progress_x);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressContainer.setClipToOutline(true);
        }
    }

    public void addProgressElement(ProgressElement element) {
        elementsList.add(element);
        View progressElement= getProgressElementView(element);
        progressElement.setTranslationX(rootView.getWidth());
        progressElementsContainer.addView(progressElement);

        animateWithSpring(progressElement);
    }

    private View getProgressElementView(ProgressElement element) {
        View view = new View(getContext());
        final int elementWidth = getProgressElementWidth(element.getAmount());
        childrenWidth += elementWidth;
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(elementWidth,
                        getResources().getDimensionPixelSize(R.dimen.progress_height));
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(element.getColor());
        return view;
    }

    private int getProgressElementWidth(int elementAmount) {
        int value = (elementAmount * rootView.getWidth()) / max;
        return value;
    }

    public void toggleElementsToFillBar() {
        float from = progressElementsContainer.getScaleX() > 1.01f ? progressElementsContainer.getScaleX() : 1f;
        float to = progressElementsContainer.getScaleX() > 1.01f ? 1f : (float)rootView.getWidth()/(float)childrenWidth;
        animateWidth(from, to);
    }

    private void animateWithSpring(View targetView) {
        SpringAnimation springX = new SpringAnimation(targetView,
                new FloatPropertyCompat<View>("translationX") {

                    @Override
                    public float getValue(View view) {
                        return view.getTranslationX();
                    }

                    @Override
                    public void setValue(View view, float value) {
                        view.setTranslationX(value);
                    }
                });

        SpringForce springForceX = new SpringForce(0.1f);
        springForceX.setStiffness(SpringForce.STIFFNESS_LOW);
        springForceX.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        springX.setSpring(springForceX);
        springX.start();
    }

    private void animateWidth(float from, float to) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        progressElementsContainer.setPivotX(0);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (Float) animation.getAnimatedValue();

                if (progressElementsContainer != null) {
                    try {
                        progressElementsContainer.setScaleX(scale);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });

        animator.setDuration(animationDuration);
        animator.start();
    }

    public void setMax(int max) {
        if(max != this.max && max > 0) {
            this.max = max;
            if(progressElementsContainer != null && progressElementsContainer.getChildCount() > 0) {
                for(int i = 0; i < progressElementsContainer.getChildCount(); i++) {
                    int width = getProgressElementWidth(elementsList.get(i).getAmount());
                    View child = progressElementsContainer.getChildAt(i);
                    LinearLayout.LayoutParams childParams = (LinearLayout.LayoutParams) child.getLayoutParams();
                    childParams.width = width;
                    child.setLayoutParams(childParams);
                }
            }
        }
    }
}
