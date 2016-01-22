#!/bin/sh

lein run > middle.html
cat html/header.html > CodeCoverage.html
cat middle.html      >> CodeCoverage.html
cat html/footer.html >> CodeCoverage.html
rm middle.html

