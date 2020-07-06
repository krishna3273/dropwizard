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

let main=async()=>{

    const node = document.getElementById("text");
    node.addEventListener("keyup", function(event) {
        if (event.key === "Enter") {
            document.getElementById("button").click();
        }
    });

    $("#button").on('click',async()=>{
        $("body div").remove();
        res=await httpGet("http://localhost:5000/plot/"+$("#text").val()+".json")
        console.log(res)
        values=res["values"]
        labels=res["labels"]
        titles=res["titles"]
        sub=res["sub_titles"]
        for(let i=0;i<titles.length;i++){
            let series=[]
            series[0]={name:`${titles[i]}-${sub[i]}`,data:values[i]}
            plot(series,labels[i],titles[i],sub[i],i);
        }
    })
}

main()