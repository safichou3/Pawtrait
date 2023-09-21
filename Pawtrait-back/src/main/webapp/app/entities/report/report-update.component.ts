import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

import PhotoService from '@/entities/photo/photo.service';
import { IPhoto } from '@/shared/model/photo.model';

import { IReport, Report } from '@/shared/model/report.model';
import ReportService from './report.service';

const validations: any = {
  report: {
    type: {},
    description: {},
    resolutionDate: {},
    status: {},
    createdAt: {},
    updatedAt: {},
    deletedAt: {},
  },
};

@Component({
  validations,
})
export default class ReportUpdate extends Vue {
  @Inject('reportService') private reportService: () => ReportService;
  @Inject('alertService') private alertService: () => AlertService;

  public report: IReport = new Report();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('photoService') private photoService: () => PhotoService;

  public photos: IPhoto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.reportId) {
        vm.retrieveReport(to.params.reportId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.report.id) {
      this.reportService()
        .update(this.report)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Report is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.reportService()
        .create(this.report)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Report is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.report[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.report[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.report[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.report[field] = null;
    }
  }

  public retrieveReport(reportId): void {
    this.reportService()
      .find(reportId)
      .then(res => {
        res.resolutionDate = new Date(res.resolutionDate);
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        res.deletedAt = new Date(res.deletedAt);
        this.report = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.photoService()
      .retrieve()
      .then(res => {
        this.photos = res.data;
      });
  }
}
