import { ITag } from '@/shared/model/tag.model';
import { IPhoto } from '@/shared/model/photo.model';

export interface IPhotoTag {
  id?: string;
  createdAt?: Date | null;
  updatedAt?: Date | null;
  deletedAt?: Date | null;
  user?: ITag | null;
  photo?: IPhoto | null;
}

export class PhotoTag implements IPhotoTag {
  constructor(
    public id?: string,
    public createdAt?: Date | null,
    public updatedAt?: Date | null,
    public deletedAt?: Date | null,
    public user?: ITag | null,
    public photo?: IPhoto | null
  ) {}
}
