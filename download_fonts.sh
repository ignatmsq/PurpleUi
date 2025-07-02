#!/bin/bash

# Create font directory if it doesn't exist
mkdir -p app/src/main/res/font

# Download Nunito fonts
curl -L "https://raw.githubusercontent.com/googlefonts/nunito/main/fonts/variable/Nunito%5Bwght%5D.ttf" -o "app/src/main/res/font/nunito_regular.ttf"
cp "app/src/main/res/font/nunito_regular.ttf" "app/src/main/res/font/nunito_bold.ttf"
cp "app/src/main/res/font/nunito_regular.ttf" "app/src/main/res/font/nunito_extrabold.ttf" 