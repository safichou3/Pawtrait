import { Component, Vue, Inject } from 'vue-property-decorator';

import { IReport } from '@/shared/model/report.model';
import ReportService from './report.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ReportDetails extends Vue {
  @Inject('reportService') private reportService: () => ReportService;
  @Inject('alertService') private alertService: () => AlertService;

  public report: IReport = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.reportId) {
        vm.retrieveReport(to.params.reportId);
      }
    });
  }

  public retrieveReport(reportId) {
    this.reportService()
      .find(reportId)
      .then(res => {
        this.report = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
