import { IUser } from '@/shared/model/user.model';
import { IPhoto } from '@/shared/model/photo.model';

export interface IReport {
  id?: string;
  type?: string | null;
  description?: string | null;
  resolutionDate?: Date | null;
  status?: string | null;
  createdAt?: Date | null;
  updatedAt?: Date | null;
  deletedAt?: Date | null;
  reportedBy?: IUser | null;
  resolvedBy?: IUser | null;
  photo?: IPhoto | null;
}

export class Report implements IReport {
  constructor(
    public id?: string,
    public type?: string | null,
    public description?: string | null,
    public resolutionDate?: Date | null,
    public status?: string | null,
    public createdAt?: Date | null,
    public updatedAt?: Date | null,
    public deletedAt?: Date | null,
    public reportedBy?: IUser | null,
    public resolvedBy?: IUser | null,
    public photo?: IPhoto | null
  ) {}
}
