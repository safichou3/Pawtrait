/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import ReportService from '@/entities/report/report.service';
import { Report } from '@/shared/model/report.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Report Service', () => {
    let service: ReportService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ReportService();
      currentDate = new Date();
      elemDefault = new Report('ABC', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            resolutionDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            deletedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('ABC').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Report', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ABC',
            resolutionDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            deletedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            resolutionDate: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate,
            deletedAt: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Report', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Report', async () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            description: 'BBBBBB',
            resolutionDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            deletedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resolutionDate: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate,
            deletedAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Report', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Report', async () => {
        const patchObject = Object.assign(
          {
            type: 'BBBBBB',
            description: 'BBBBBB',
            resolutionDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            deletedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          new Report()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            resolutionDate: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate,
            deletedAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Report', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Report', async () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            description: 'BBBBBB',
            resolutionDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updatedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            deletedAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            resolutionDate: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate,
            deletedAt: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Report', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Report', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Report', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
