# Starting template

This README will need to contain a description of your project, how to run it, how to set up the development
environment, and who worked on it. This information can be added throughout the course, except for the names of the
group members. Add your own name (do not add the names for others!) to the section below.

## Description of project

## Group members

| Profile Picture | Name | Email |
|---|---|---|
| ![Max de Groot](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/5033/avatar.png?width=96) | Max de Groot | M.R.degroot-1@student.tudelft.nl |
| ![Demetra Carata](https://secure.gravatar.com/avatar/1872d0d272df591c5e120f0574cf657f?s=192&d=identicon&size=96&width=96) | Ioana Demetra Carata Dejoianu | I.D.CarataDejoianu@student.tudelft.nl |
| ![Jakub Kontak](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4493/avatar.png?width=96&size=50) | Jakub Kontak | J.Kontak@student.tudelft.nl |
| ![Daniël Ravensbergen](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/5010/avatar.png?width=96&size=50) | Daniël Ravensbergen | D.G.S.Ravensbergen@student.tudelft.nl |
| ![Lauri Kesküll](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4943/avatar.png?&width=96&size=96) | Lauri Kesküll | L.Keskull@student.tudelft.nl |
| ![Alexandra Carutasu](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4756/avatar.png?width=96&size=50) | Alexandra Carutasu | A.Carutasu@student.tudelft.nl |

<!-- Instructions (remove once assignment has been completed -->
<!-- - Add (only!) your own name to the table above (use Markdown formatting) -->
<!-- - Mention your *student* email address -->
<!-- - Preferably add a recognizable photo, otherwise add your GitLab photo -->
<!-- - (please make sure the photos have the same size) --> 

## How to run it

You should download the game. You should have a stable internet connection in order for the game to work properly.

## How to contribute to it

We adhere our code style to that of https://google.github.io/styleguide/javaguide.html

Follow this guide to set up your IDE accordingly.

1. Install the plugin
    - Go to File -> Settings -> Plugins
    - Select the Marketplace tab
    - Search for CheckStyle-IDEA and install it
    - Press Restart IDE
    - Press Restart
2. Configure checkstyle
    - Go to File -> Settings -> Tools -> Checkstyle
    - Make sure you have Checkstyle version 9.3
    - Set the Scan Scope to All files in project
    - Enable 'Treat Checkstyle errors as warnings'
    - Click the + Symbol to add a configuration file
    - Enter a Description (e.g. Custom checkstyle rules)
    - Press Browse
    - Select the checkstyle.xml file in the root of the project
    - Enable 'Store relative to project location'
    - Press 'Next'
    - Press 'Next' again
    - Press 'Finish'
    - Activate the newly added checkstyle rules by pressing the checkbox of the corresponding configuration file
    - Press 'Apply' to save the changes
3. Update the code style of the editor
    - Go to File -> Settings -> Editor -> Code Style
    - Select Project as scheme
    - Press the settings icon
    - Press 'Import Scheme'
    - Press 'CheckStyle Configuration'
    - Choose the checkstyle.xml file that is in the root of the project
    - Press 'OK'
    - Press 'Apply'
4. Automate reformatting (recommended)
    - Go to File -> Settings -> Tools -> Actions on Save
    - Enable the following actions
        - Reformat code
        - Optimize imports
        - Rearrange code
    - Press Apply

## Copyright / License (opt.)

OOPP Group No. 17
