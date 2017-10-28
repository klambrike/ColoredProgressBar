# Description

![Populating bar and animating width](https://raw.github.com/klambrike/ColoredProgressBar/master/reference/colored_progress_bar_demo.gif "Populating bar and animating width")

## Usage
Add element to layout:

```xml
<ee.klambrike.library.ColoredProgress
        android:id="@+id/colored_progress"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        />
```        
Add elements to bar:
   
```java
ColoredProgress coloredProgress = findViewById(R.id.colored_progress);
coloredProgress.addProgressElement(
        new ProgressElement(elementColor, elementProgressAmount);
```
Toggle whether elements fill the whole progress bar or only according the amount assigned to them:

```java
coloredProgress.toggleElementsToFillBar();
```