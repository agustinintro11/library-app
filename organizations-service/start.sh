if [ ${ENV} = "DEV" ]; then 
    nodemon ./app.js
else
    node ./app.js
fi

