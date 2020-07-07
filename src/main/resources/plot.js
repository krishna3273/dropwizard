async function httpGet(url) {
    const response = await fetch(url, {method:'get'});
    return response.json();
}

let plot=(series,labels,entity,metric_name,plot_num)=>{
    var chart = {
        zoomType: 'x',
    };
    var subtitle = {
        text:metric_name
    };
    var plotOptions = {
        series:{
            turboThreshold:5000
        },
        marker: {
            radius: 1
        },
        lineWidth: 1,
        states: {
            hover: {
                lineWidth: 1
            }
        },
        threshold: null
    };
    var title = {
        text:entity
    };
    var xAxis = {
        categories:labels
    };
    var yAxis = {
        title: {
            text: 'Metric-Value'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    } ;
    var tooltip = {
        valueSuffix: ''
    }
    var legend = {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    };
    var properties = {};
    properties.chart = chart;
    properties.title = title;
    properties.subtitle = subtitle;
    properties.legend = legend;
    properties.xAxis = xAxis;
    properties.yAxis = yAxis;
    properties.series = series;
    properties.plotOptions = plotOptions;
    properties.tooltip = tooltip;
    console.log(properties)
    let plot_div=document.createElement('div');
    document.body.appendChild((plot_div));
    plot_div.setAttribute("id",`plot-${plot_num}`)
    $(`#plot-${plot_num}`).highcharts(properties);
}

let main=()=>{

    const node = document.getElementById("text");
    node.addEventListener("keyup", function(event) {
        if (event.key === "Enter") {
            document.getElementById("button").click();
        }
    });

    $("#button").on('click',async()=>{
        $("body div").remove();
        let res=await httpGet("http://localhost:5000/plot/"+$("#text").val()+".json")
        // console.log(res)
        // console.log("krishna")
        plot_num=0
        for(const key in res){
            // console.log(key,res[key])
            for (const innerkey in res[key]){
                let currList=res[key][innerkey]["metricData"];
                let series=[]
                let labels=[]
                let values=[]
                // console.log(typeof (currList))
                for(let i=0;i<currList.length;i++){
                    let datavalues=currList[i];
                    for(const timestamp in datavalues){
                        labels[i]=timestamp;
                        // console.log(`timestamp=${timestamp}`)
                        values[i]=datavalues[timestamp].value;
                        // console.log(`value=${values[i]}`)
                    }
                }
                series[0]={"name":key+innerkey,"data":values}
                plot(series,labels,key,innerkey,plot_num)
                plot_num++
            }
        }
    })
}

main()