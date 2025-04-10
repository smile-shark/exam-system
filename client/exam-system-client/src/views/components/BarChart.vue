<template>
    <div :style="{ width: width, height: height }" ref="chartRef"></div>
  </template>

<script>
import * as echarts from 'echarts';

export default {
  props: {
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '400px'
    },
    chartData: {
      type: Object,
      default: () => ({
        xAxisData: ['类目1', '类目2', '类目3', '类目4'],
        seriesData: [23, 30, 16, 45]
      })
    }
  },
  data() {
    return {
      chart: null
    };
  },
  watch: {
    chartData: {
      deep: true,
      handler() {
        this.initChart();
      }
    }
  },
  mounted() {
    this.initChart();
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose();
    }
  },
  methods: {
    initChart() {
      if (this.chart) {
        this.chart.dispose();
      }
      this.chart = echarts.init(this.$refs.chartRef);
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: this.chartData.xAxisData
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.chartData.seriesData,
            type: 'bar'
          }
        ]
      });
    }
  }
};
</script>